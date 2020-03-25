package ch.epfl.sdp.artificial_intelligence;

import java.util.List;

import ch.epfl.sdp.entity.EntityType;
import ch.epfl.sdp.entity.Player;

public class Enemy extends MovingArtificialEntity {
    private Behaviour behaviour;
    private List<Player> players; // For now I use a list of players, but it could be nice to have
    // a static manager of players.
    private int damage;
    private float dps; // damage per second
    private double detectionDistance;
    private int timeAttack;
    private int timeWandering;
    private LocalBounds patrolBounds;
    private Boundable maxBounds;
    private boolean waiting;

    public Enemy(List<Player> players, int damage, float dps, float detectionDistance, double aoeRadius , LocalBounds patrolBounds, Boundable maxBounds) {
        this.damage = damage;
        this.dps = dps;
        this.detectionDistance = detectionDistance;
        this.players = players;
        behaviour = Behaviour.WAIT;
        timeAttack = 100; // Needs calibration
        timeWandering = 100;
        this.patrolBounds = patrolBounds;
        this.maxBounds = maxBounds;
        this.waiting = false;
        if (aoeRadius < detectionDistance) {
            this.setAoeRadius(aoeRadius);
        }
    }

    public Behaviour getBehaviour() {
        return behaviour;
    }

    public int getTimeAttack() {
        return timeAttack;
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
            double attackRange = this.getAoeRadius();
            Player target = playerDetected(attackRange);
            if (target != null) {
                target.setHealthPoints(target.getHealthPoints() - damage * dps);
            } else {
                setMoving(true);
                behaviour = Behaviour.CHASE;
            }

            timeAttack = 100;
        } else {
            timeAttack -= 1;
        }

        checkWaiting();
    }

    public boolean checkWaiting() {
        if (waiting) {
            super.setMoving(false);
            behaviour = Behaviour.WAIT;
            return true;
        }

        return false;
    }

    private Player selectClosestPlayer() {
        Player target = null;
        double minDistance = Double.MAX_VALUE;
        double currDistance;

        for (Player player : players) {
            currDistance = player.getPosition().toCartesian().distanceFrom(getPosition()) - player.getAoeRadius();
            if (currDistance < minDistance && player.isAlive() && !player.isShielded()) {
                minDistance = currDistance;
                target = player;
            }
        }

        return target;
    }

    private void chase() {
        Player target = playerDetected(detectionDistance);

        if (target != null) {
            orientToTarget(target);
            if (playerDetected(this.getAoeRadius())!= null) {
                super.setMoving(false);
                behaviour = Behaviour.ATTACK;
            }
        } else {
            super.setBounds(patrolBounds);
            setForceMove(true);
            super.setVelocity(super.getVelocity() / 2);
            behaviour = Behaviour.PATROL;
        }

        checkWaiting();
    }

    private void patrol() {
        if (!patrolBounds.isInside(super.getPosition())) {
            orientToTarget(patrolBounds);
        } else {
            setForceMove(false);
        }

        if (playerDetected(detectionDistance) != null) {
            super.setBounds(maxBounds);
            super.setVelocity(super.getVelocity() * 2);
            super.setMoving(true);
            behaviour = Behaviour.CHASE;
        }

        checkWaiting();
    }

    private void orientToTarget(Localizable localizable) {
        setOrientation(getPosition().toCartesian().vector(localizable.getPosition()).toPolar().arg2);
    }

    private Player playerDetected(double distance) {
        Player target = selectClosestPlayer();
        if (target.getPosition().toCartesian().distanceFrom(getPosition()) - target.getAoeRadius() < distance) {
            return target;
        } else {
            return null;
        }
    }

    private void wander() {
        if (timeWandering <= 0) {
            super.setBounds(patrolBounds);
            setForceMove(true);
            behaviour = Behaviour.PATROL;
            timeWandering = 100;
        } else {
            timeWandering -= 1;
        }

        if (checkWaiting()) {
            timeWandering = 100;
        }
    }

    @Override
    public void update() {
        super.update();
        behave();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ENEMY;
    }

}
