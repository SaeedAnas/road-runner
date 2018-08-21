package com.acmerobotics.roadrunner.drive

import com.acmerobotics.roadrunner.Pose2d

/**
 * This class provides basic functionality of a mecanum drive using on [MecanumKinematics].
 *
 * @param trackWidth lateral distance between pairs of wheels on different sides of the robot
 * @param wheelBase distance between pairs of wheels on the same side of the robot
 */
abstract class SwerveDrive @JvmOverloads constructor(
        val trackWidth: Double,
        val wheelBase: Double = trackWidth
) : Drive() {
    override var poseEstimate: Pose2d = Pose2d()
        set(value) {
            lastWheelPositions = emptyList()
            field = value
        }
    private var lastWheelPositions = emptyList<Double>()

    override fun setVelocity(poseVelocity: Pose2d) {
        val motorPowers = SwerveKinematics.robotToWheelVelocities(poseVelocity, trackWidth, wheelBase)
        val moduleOrientations = SwerveKinematics.robotToModuleOrientations(poseVelocity, trackWidth, wheelBase)
        setMotorPowers(motorPowers[0], motorPowers[1], motorPowers[2], motorPowers[3])
        setModuleOrientations(moduleOrientations[0], moduleOrientations[1], moduleOrientations[2], moduleOrientations[3])
    }

    // TODO: move to base class? note: could get tricky with the inherited properties
    override fun updatePoseEstimate() {
        // TODO!!!
//        val wheelPositions = getWheelPositions()
//        if (lastWheelPositions.isNotEmpty()) {
//            val positionDeltas = wheelPositions
//                    .zip(lastWheelPositions)
//                    .map { it.first - it.second }
//            val robotPoseDelta = MecanumKinematics.wheelToRobotVelocities(positionDeltas, wheelBase, trackWidth)
//            val newHeading = poseEstimate.heading + robotPoseDelta.heading
//            poseEstimate += Pose2d(robotPoseDelta.pos().rotated(newHeading), robotPoseDelta.heading)
//        }
//        lastWheelPositions = wheelPositions
    }

    /**
     * Sets the following motor powers (normalized voltages). All arguments are on the interval `[-1.0, 1.0]`.
     */
    abstract fun setMotorPowers(frontLeft: Double, rearLeft: Double, rearRight: Double, frontRight: Double)

    abstract fun setModuleOrientations(frontLeft: Double, rearLeft: Double, rearRight: Double, frontRight: Double)

    /**
     * Returns the positions of the wheels in linear distance units.
     */
    abstract fun getWheelPositions(): List<Double>

    abstract fun getModuleOrientations(): List<Double>
}