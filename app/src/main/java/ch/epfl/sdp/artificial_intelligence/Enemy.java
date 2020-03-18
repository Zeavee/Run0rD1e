package ch.epfl.sdp.artificial_intelligence;

import java.util.List;

import ch.epfl.sdp.Player;

public class Enemy extends MovingEntity {
    private Behaviour behaviour;
    private List<Player> players; // For now I use a list of players, but it could be nice to have
    // a static manager of players.
    private int damage;
    private float dps; // damage per second
    private float detectionDistance;
    private int timeAttack;
    private int timeWandering;
    private LocalBounds patrolBounds;
    private Boundable maxBounds;
    private boolean waiting;

    public Enemy(List<Player> players, int damage, float dps, float detectionDistance, LocalBounds patrolBounds, Boundable maxBounds) {
        this.damage = damage;
        this.dps = dps;
        this.detectionDistance = detectionDistance;
        this.players = players;
        behaviour = Behaviour.WAIT;
        timeAttack = 100; // Needs calibration
        timeWandering = 1000;
        this.patrolBounds = patrolBounds;
        this.maxBounds = maxBounds;
        this.waiting = false;
    }

    private void behave() {
        switch (behaviour) {
            case ATTACK:
                attack();
                break;
            case CHASE:
                chase();
                break;
            case PATROL:
                patrol();
                break;
            case WAIT:
                _wait();
                break;
            case WANDER:
                wander();
                break;
        }
    }

    private void _wait() {
        if (!waiting) {
            super.setMoving(true);
            behaviour = Behaviour.WANDER;
        }
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    private void attack() {
        if (timeAttack <= 0) {
            Player target = selectClosestPlayer();

            if (target != null) {
                target.setHealthPoints(target.getHealthPoints() - damage * dps);
            }

            timeAttack = 100;
        } else {
            timeAttack -= 1;
        }

        if (waiting) {
            super.setMoving(false);
            behaviour = Behaviour.WAIT;
        }
    }

    private Player selectClosestPlayer() {
        Player target = null;
        float minDistance = Float.MAX_VALUE;

        for (Player player : players) {
            if (player.getPosition().toCartesian().distanceFrom(getPosition()) < minDistance && player.isAlive()) {
                target = player;
            }
        }

        return target;
    }

    private void chase() {
        if (playerDetected()) {
            Player target = selectClosestPlayer();
            orientToTarget(target);
        } else if (waiting) {
            super.setMoving(false);
            behaviour = Behaviour.WAIT;
        } else {
            super.setBounds(patrolBounds);
            super.setVelocity(super.getVelocity() / 2);
            behaviour = Behaviour.PATROL;
        }
    }

    private void patrol() {
        if (!patrolBounds.isInside(super.getPosition())) {
            orientToTarget(patrolBounds);
        }

        if (playerDetected()) {
            super.setBounds(maxBounds);
            super.setVelocity(super.getVelocity() * 2);
            super.setMoving(true);
            behaviour = Behaviour.CHASE;
        }

        if (waiting) {
            super.setMoving(false);
            behaviour = Behaviour.WAIT;
        }
    }

    private void orientToTarget(Localizable localizable) {
        setOrientation(getPosition().toCartesian().vector(localizable.getPosition()).toPolar().arg2);
    }

    private boolean playerDetected() {
        Player target = selectClosestPlayer();
        return target.getPosition().toCartesian().distanceFrom(getPosition()) < detectionDistance;
    }

    private void wander() {
        if (timeWandering <= 0) {
            super.setBounds(patrolBounds);
            behaviour = Behaviour.PATROL;
        } else {
            timeWandering -= 1;
        }
    }

    @Override
    public void update() {
        super.update();
        behave();
    }
}
