package kelvin.fiveminsurvival.entity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import kelvin.fiveminsurvival.init.EntityRegistry;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SpearEntity extends TridentEntity {

	private ItemStack thrownStack = null;
    private static final DataParameter<ItemStack> THROWN_STACK = EntityDataManager.createKey(SpearEntity.class, DataSerializers.ITEMSTACK);

	
	private List<Entity> pierced = new ArrayList<Entity>();
	private boolean dealtDamage = false;
	
	private Entity hit = null;
	private Entity lastHit = null;
	
    public SpearEntity(EntityType<? extends SpearEntity> p_i50148_1_, World p_i50148_2_) {
        super(p_i50148_1_, p_i50148_2_);
    }

    public SpearEntity(World p_i48790_1_, LivingEntity p_i48790_2_, ItemStack p_i48790_3_) {
        super(EntityRegistry.SPEAR_ENTITY.get(), p_i48790_1_);
        this.setShooter(p_i48790_2_);
        this.setPosition(p_i48790_2_.getPosX(), p_i48790_2_.getPosY() + p_i48790_2_.getEyeHeight(), p_i48790_2_.getPosZ());
        this.thrownStack = p_i48790_3_;
        this.dataManager.set(THROWN_STACK, thrownStack);
    }

    protected ItemStack getArrowStack() {
        return this.thrownStack.copy();
    }
    
    @OnlyIn(Dist.CLIENT)
    public SpearEntity(World p_i48791_1_, double x, double y, double z) {
        super(EntityRegistry.SPEAR_ENTITY.get(), p_i48791_1_);
        this.setPosition(x, y, z);
    }

    public SpearEntity(World world) {
        super(EntityRegistry.SPEAR_ENTITY.get(), world);
    }

    public EntitySize getSize(Pose pose) {
        return EntitySize.fixed(0.5f, 0.5f);
    }
    public void tick() {
    	super.tick();
    	RayTraceResult result = super.rayTraceEntities(getPositionVec(), new Vector3d(getPosX(), getPosY(), getPosZ()).add(getMotion()));
    	if (result instanceof EntityRayTraceResult) {
    		if (((EntityRayTraceResult)result).getEntity() != null)
    		onEntityHit((EntityRayTraceResult)result);
    	}
    	this.lookAt(Type.EYES, getPositionVec().add(new Vector3d(this.getMotion().x, this.getMotion().y, this.getMotion().z).normalize()));
    }
    
    protected void registerData() {
        super.registerData();
        this.dataManager.register(THROWN_STACK, new ItemStack(Items.AIR));
    }
    
    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Nullable
    protected EntityRayTraceResult rayTraceEntities(Vector3d startVec, Vector3d endVec) {
    	//EntityRayTraceResult result = super.rayTraceEntities(startVec, endVec);
        //return this.dealtDamage || (lastHit == hit && hit != null) ? null : result;
    	return null;
    }
    
    protected void onEntityHit(EntityRayTraceResult result) {
    	
    	
    	lastHit = hit;
    	hit = result.getEntity();
    	
    	if (hit == lastHit) return;
    	
    	ItemStack thrownStack = this.dataManager.get(THROWN_STACK);
    	
    	if (thrownStack == null) {
    		super.onEntityHit(result);
    		return;
    	}
    	if (result.getEntity() != null) {
    		if (getFireTimer() > 0) {
        		result.getEntity().setFire(5);
        	}
	    	if (this.getPierceLevel() > 0) {
	    		if (pierced.contains(result.getEntity())) {
	        		return;
	        	}
	            if (pierced.size() >= this.getPierceLevel() + 1) {  
	               this.dealtDamage = true;
	               super.onEntityHit(result);
	               return;
	            }
	
	            pierced.add(result.getEntity());
	
	            Entity entity = result.getEntity();
	            float f = (float)this.getDamage() * 1.5f;
	            if (entity instanceof LivingEntity) {
	               LivingEntity livingentity = (LivingEntity)entity;
	               f += EnchantmentHelper.getModifierForCreature(thrownStack, livingentity.getCreatureAttribute());
	            }
	
	            Entity shooter = this.func_234616_v_();
	            DamageSource damagesource = DamageSource.causeTridentDamage(this, (Entity)(shooter == null ? this : shooter));
	            if (entity.attackEntityFrom(damagesource, f)) {
	               if (entity.getType() == EntityType.ENDERMAN) {
	                  return;
	               }
	               if (entity instanceof LivingEntity) {
	                  LivingEntity living = (LivingEntity)entity;
	                  if (shooter instanceof LivingEntity) {
	                     EnchantmentHelper.applyThornEnchantments(living, shooter);
	                     EnchantmentHelper.applyArthropodEnchantments((LivingEntity)shooter, living);
	                  }
	               }
	            }
	            
	         } else {
	        	 super.onEntityHit(result);
	         }
    	}
    }
    
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("Spear", 10)) {
            this.thrownStack = ItemStack.read(compound.getCompound("Spear"));
            this.dataManager.set(THROWN_STACK, thrownStack);
        }

        this.dealtDamage = compound.getBoolean("DealtDamage");
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.put("Spear", this.thrownStack.write(new CompoundNBT()));
        compound.putBoolean("DealtDamage", this.dealtDamage);
    }
}
