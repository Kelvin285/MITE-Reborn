package kelvin.fiveminsurvival.main.network;

import java.util.function.Supplier;

import kelvin.fiveminsurvival.main.resources.Resources;
import kelvin.fiveminsurvival.survival.food.Nutrients;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendNutrients {
	
	public Nutrients nutrients;
	
	public SPacketSendNutrients(Nutrients nutrients) {
		this.nutrients = nutrients;
	}
	
	public SPacketSendNutrients(PacketBuffer buf) {
		this.nutrients = new Nutrients(null);
		this.nutrients.carbs = buf.readDouble();
		this.nutrients.fatty_acids = buf.readDouble();
		this.nutrients.happiness = buf.readDouble();
		this.nutrients.insulin_resistance = buf.readDouble();
		this.nutrients.phytonutrients = buf.readDouble();
		this.nutrients.protein = buf.readDouble();
		this.nutrients.sugars = buf.readDouble();
		this.nutrients.fatigue = buf.readDouble();
		this.nutrients.negativeLevel = buf.readInt();
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeDouble(this.nutrients.carbs);
		buf.writeDouble(this.nutrients.fatty_acids);
		buf.writeDouble(this.nutrients.happiness);
		buf.writeDouble(this.nutrients.insulin_resistance);
		buf.writeDouble(this.nutrients.phytonutrients);
		buf.writeDouble(this.nutrients.protein);
		buf.writeDouble(this.nutrients.sugars);
		buf.writeDouble(this.nutrients.fatigue);
		buf.writeInt(this.nutrients.negativeLevel);
    }
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> Resources.clientNutrients = this.nutrients);
        ctx.get().setPacketHandled(true);
	}
}
