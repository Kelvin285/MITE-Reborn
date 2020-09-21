package kelvin.fiveminsurvival.entity;

import java.util.List;

import kelvin.fiveminsurvival.init.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAttackSquid extends SquidEntity {

	private float rotationVelocity;
	private float randomMotionSpeed;
	private float rotateSpeed;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	public EntityAttackSquid(EntityType<? extends SquidEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	public EntityAttackSquid(World worldIn) {
		super(EntityRegistry.ATTACK_SQUID.get(), worldIn);
	}

	public void livingTick() {
		super.livingTick();
		
		List<? extends PlayerEntity> players = world.getPlayers();
		PlayerEntity target = null;
		double dist = 25;
		for (PlayerEntity player : players) {
			double d1 = player.getPositionVec().distanceTo(getPositionVec());
			if (d1 <= dist) {
				dist = d1;
				target = player;
			}
		}
		int dirX = 0;
		int dirY = 0;
		int dirZ = 0;
		boolean squidstuff = false;
		if (!world.isRemote)
		if (target != null) {
			if (target.isInWaterOrBubbleColumn()) {
				squidstuff = true;
				if (getPosX() > target.getPosX()) dirX = -1; else dirX = 1;
				if (getPosY() > target.getPosY()) dirY = -1; else dirY = 1;
				if (getPosZ() > target.getPosZ()) dirZ = -1; else dirZ = 1;
				
				double acceleration = 0.05f;
				double speed = 2f;
				
				double velX = getMotion().x;
				double velY = getMotion().y;
				double velZ = getMotion().z;
				
				velX += dirX * acceleration;
				velY += dirY * acceleration;
				velZ += dirZ * acceleration;
				
				if (Math.abs(velX) > speed) {
					velX -= dirX * acceleration;
				}
				
				if (Math.abs(velZ) > speed) {
					velZ -= dirZ * acceleration;
				}
				
				if (Math.abs(velY) > speed) {
					velY -= dirY * acceleration;
				}
				setMotion(velX, velY, velZ);
				
				rotationYaw = 0;
				rotationYawHead = 0;
				squidYaw = 0;
				prevSquidYaw = 0;
				prevRotationYaw = 0;
				prevRotationYawHead = 0;

				if (getPositionVec().distanceTo(target.getPositionVec()) <= 2) {
					target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20 * 10, 2));
					setMotion(getMotion().x, getMotion().y-0.1, getMotion().z);
				}
			}
		}
	}
	
	public void doSquidThing(boolean move) {
		this.prevSquidPitch = this.squidPitch;
	      this.prevSquidYaw = this.squidYaw;
	      this.prevSquidRotation = this.squidRotation;
	      this.lastTentacleAngle = this.tentacleAngle;
	      this.squidRotation += this.rotationVelocity;
	      if ((double)this.squidRotation > (Math.PI * 2D)) {
	         if (this.world.isRemote) {
	            this.squidRotation = ((float)Math.PI * 2F);
	         } else {
	            this.squidRotation = (float)((double)this.squidRotation - (Math.PI * 2D));
	            if (this.rand.nextInt(10) == 0) {
	               this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
	            }

	            this.world.setEntityState(this, (byte)19);
	         }
	      }

	      if (this.isInWaterOrBubbleColumn()) {
	         if (this.squidRotation < (float)Math.PI) {
	            float f = this.squidRotation / (float)Math.PI;
	            this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
	            if ((double)f > 0.75D) {
	               this.randomMotionSpeed = 1.0F;
	               this.rotateSpeed = 1.0F;
	            } else {
	               this.rotateSpeed *= 0.8F;
	            }
	         } else {
	            this.tentacleAngle = 0.0F;
	            this.randomMotionSpeed *= 0.9F;
	            this.rotateSpeed *= 0.99F;
	         }

	         if (!this.world.isRemote) {
	        	 if (move)
	            this.setMotion(this.randomMotionVecX * this.randomMotionSpeed, this.randomMotionVecY * this.randomMotionSpeed, this.randomMotionVecZ * this.randomMotionSpeed);
	         }

	         Vec3d vec3d = this.getMotion();
	         float f1 = MathHelper.sqrt(horizontalMag(vec3d));
	         this.renderYawOffset += (-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180F / (float)Math.PI) - this.renderYawOffset) * 0.1F;
	         this.rotationYaw = this.renderYawOffset;
	         this.squidYaw = (float)((double)this.squidYaw + Math.PI * (double)this.rotateSpeed * 1.5D);
	         this.squidPitch += (-((float)MathHelper.atan2(f1, vec3d.y)) * (180F / (float)Math.PI) - this.squidPitch) * 0.1F;
	      } else {
	         this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;
	         if (!this.world.isRemote) {
	            double d0 = this.getMotion().y;
	            if (this.isPotionActive(Effects.LEVITATION)) {
	               d0 = 0.05D * (double)(this.getActivePotionEffect(Effects.LEVITATION).getAmplifier() + 1);
	            } else if (!this.hasNoGravity()) {
	               d0 -= 0.08D;
	            }
	            if (move)
	            this.setMotion(0.0D, d0 * (double)0.98F, 0.0D);
	         }

	         this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
	      }
	}
}
