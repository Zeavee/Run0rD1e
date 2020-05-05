package ch.epfl.sdp.entity;

import ch.epfl.sdp.artificial_intelligence.Behaviour;
import ch.epfl.sdp.artificial_intelligence.MovingArtificialEntity;
import ch.epfl.sdp.game.GameThread;
import ch.epfl.sdp.geometry.Area;
import ch.epfl.sdp.geometry.LocalArea;
import ch.epfl.sdp.geometry.Positionable;
import ch.epfl.sdp.geometry.RectangleArea;
import ch.epfl.sdp.geometry.UnboundedArea;
import ch.epfl.sdp.geometry.Vector;

/**
 * Represents a hostile entity.
 * The finite state machine is as follows:
 * CHASE<------->PATROL+-----------+
 * ^ +            ^              |
 * | |            |              v
 * | +------------------------>WAIT
 * | |            |              ^
 * v +            +              |
 * ATTACK         WANDER<-----------+
 */
public class Enemy extends MovingArtificialEntity {
    private int id;
    private Behaviour behaviour;
    /**
     * The enemy's attack strength
     */
    private int damage;
    /**
     * The rate of damage per second
     */
    private float damageRate;
    private double detectionDistance;
    /**
     * The number of frames before the next attack.
     */
    private int attackTimeDelay;
    /**
     * The number of frames before enemy is in the patrol state.
     */
    private int wanderingTimeDelay;
    private LocalArea patrolBounds;
    /**
     * The flag to decide to go in wait state.
     */
    private boolean waiting;

    /**
     * Creates a default enemy
     */
    public Enemy() {
        super();
        super.setAoeRadius(1);
        super.getMovement().setVelocity(50);
        super.setMoving(true);
        super.setArea(new RectangleArea(20000, 10000));
        this.damage = 1;
        this.damageRate = 1;
        this.detectionDistance = 1;
        behaviour = Behaviour.PATROL;
        attackTimeDelay = 30; // Needs calibration
        wanderingTimeDelay = GameThread.FPS;
        this.patrolBounds = new LocalArea(new UnboundedArea(), getPosition());
        this.waiting = false;
    }

    /**
     * Creates an enemy that is bounded in an area.
     *
     * @param id           The enemy's id.
     * @param patrolBounds The enemy's patrol area.
     * @param maxBounds    The enemy's maximum visitable area.
     */
    public Enemy(int id, LocalArea patrolBounds, Area maxBounds) {
        this(id, 10, 1, 1000, 50, patrolBounds, maxBounds);
    }

    /**
     * Creates an enemy.
     *
     * @param id                The enemy's id.
     * @param damage            The enemy's attack's strength.
     * @param damageRate        The enemy's damage rate per second.
     * @param detectionDistance The enemy's detection range for chasing player when in patrol state.
     * @param aoeRadius         The enemy's attack range when in chase state or attack state.
     * @param patrolBounds      The enemy's patrol area.
     * @param maxBounds         The enemy's maximum visitable area.
     */
    public Enemy(int id, int damage, float damageRate, float detectionDistance, double aoeRadius, LocalArea patrolBounds, Area maxBounds) {
        super();
        super.getMovement().setVelocity(25);
        super.setMoving(true);
        super.setArea(maxBounds);
        this.id = id;
        this.damage = damage;
        this.damageRate = damageRate;
        this.detectionDistance = detectionDistance;
        behaviour = Behaviour.WAIT;
        attackTimeDelay = GameThread.FPS; // Needs calibration
        wanderingTimeDelay = GameThread.FPS;
        this.patrolBounds = patrolBounds;
        this.waiting = false;
        if (aoeRadius < detectionDistance) {
            this.setAoeRadius(aoeRadius);
        }
    }

    /**
     * Get the unique id of the enemy
     *
     * @return The unique id of the enemy
     */
    public int getId() {
        return id;
    }

    /**
     * Set the unique id of the enemy
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the enemy's behavior.
     *
     * @return A behaviour representing the state in the finite state machine.
     */
    public Behaviour getBehaviour() {
        return behaviour;
    }

    /**
     * Gets the remaining time delay before the next attack.
     *
     * @return The remaining time delay before the next attack.
     */
    public int getAttackTimeDelay() {
        return attackTimeDelay;
    }

    /**
     * The finite state machine's controller.
     * The enemy changes it's behaviour based on the position of the players.
     */
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

    /**
     * The enemy does nothing until waiting is false.
     */
    private void _wait() {
        if (!waiting) {
            super.setMoving(true);
            behaviour = Behaviour.WANDER;
        }
    }

    /**
     * Sets the waiting flag.
     *
     * @param waiting The enemy's waiting flag to go to the wait state.
     */
    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * The enemy inflicts damage to a player after the attack time delay if the player is in
     * the attack range, otherwise it will chase the player.
     * Can also go to the wait state if the flag is enabled.
     */
    private void attack() {
        if (attackTimeDelay <= 0) {
            double attackRange = this.getAoeRadius();
            Player target = playerDetected(attackRange);
            if (target != null && !target.isShielded()) {
                target.setHealthPoints(target.getHealthPoints() - damage*damageRate);
//                PlayerManager.getInstance().addPlayerWaitingHealth(target);
            } else {
                setMoving(true);
                behaviour = Behaviour.CHASE;
            }

            attackTimeDelay = GameThread.FPS;
        } else {
            attackTimeDelay -= 1;
        }

        checkWaiting();
    }

    /**
     * Verify if the wait flag is enabled, if it's the case change the state to the wait state.
     *
     * @return True if the flag is enabled, false otherwise.
     */
    public boolean checkWaiting() {
        if (waiting) {
            super.setMoving(false);
            behaviour = Behaviour.WAIT;
            return true;
        }

        return false;
    }

    /**
     * Chase the closest player based on the enemy's detection distance.
     * If a player is detected based on the enemy's attack range, the state changes to attack.
     * If no player is detected the state is changed to the patrol state.
     * Can also go to the wait state if the flag is enabled.
     */
    private void chase() {
        Player target = playerDetected(detectionDistance);

        if (target != null) {
            orientToTarget(target);
            if (playerDetected(this.getAoeRadius()) != null) {
                super.setMoving(false);
                behaviour = Behaviour.ATTACK;
            }
        } else {
            super.setArea(patrolBounds);
            setForceMove(true);
            super.getMovement().setVelocity(super.getMovement().getVelocity() / 2);
            behaviour = Behaviour.PATROL;
        }

        checkWaiting();
    }

    /**
     * Move around the patrol area until a player is detected based on the enemy's detection distance.
     * If a player is detected the state changes to chase.
     * Can also go to the wait state if the flag is enabled.
     */
    private void patrol() {
        if (!patrolBounds.isInside(super.getPosition())) {
            orientToTarget(patrolBounds);
        } else {
            setForceMove(false);
        }

        if (playerDetected(detectionDistance) != null) {
            super.getMovement().setVelocity(super.getMovement().getVelocity() * 2);
            super.setMoving(true);
            behaviour = Behaviour.CHASE;
        }

        checkWaiting();
    }

    /**
     * Changes the angle of the direction of the enemy's movement to follow a positionable target.
     * The object can be a player or a location on the map.
     *
     * @param positionable A Positionable representing a target position.
     */
    private void orientToTarget(Positionable positionable) {
        Vector vectPos = getPosition().toVector();
        Vector targetPos = positionable.getPosition().toVector();
        getMovement().setOrientation(vectPos.subtract(targetPos).direction());
    }

    /**
     * Checks if a player was detected based on the given distance.
     *
     * @param distance The range to check for a player.
     * @return The Player if one was detected, otherwise returns null.
     */
    private Player playerDetected(double distance) {
        Player target = PlayerManager.getInstance().selectClosestPlayer(getPosition());
        if (target != null && target.getPosition().distanceFrom(getPosition()) - target.getAoeRadius() < distance) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * The enemy moves without hostile behaviour until wandering time delay is over, after this
     * the state is changed to the patrol state.
     * Can also go to the wait state if the flag is enabled.
     */
    private void wander() {
        if (wanderingTimeDelay <= 0) {
            super.setArea(patrolBounds);
            setForceMove(true);
            behaviour = Behaviour.PATROL;
            wanderingTimeDelay = GameThread.FPS;
        } else {
            wanderingTimeDelay -= 1;
        }

        if (checkWaiting()) {
            wanderingTimeDelay = GameThread.FPS;
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

