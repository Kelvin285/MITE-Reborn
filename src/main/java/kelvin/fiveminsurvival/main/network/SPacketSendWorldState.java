package kelvin.fiveminsurvival.main.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;


public class SPacketSendWorldState {
	
	public long time;
	public float rainStrength;
	
	public SPacketSendWorldState(long time, float rainStrength) {
		this.time = time;
		this.rainStrength = rainStrength;
	}
	
	public SPacketSendWorldState(PacketBuffer buf) {
		this.time = buf.readLong();
		this.rainStrength = buf.readFloat();
	}
	
	public void encode(PacketBuffer buf) {
		buf.writeLong(this.time);
		buf.writeFloat(this.rainStrength);
    }
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			  Minecraft.getInstance().world.setDayTime(time);
			  Minecraft.getInstance().world.setRainStrength(rainStrength);
        });
        ctx.get().setPacketHandled(true);
	}
}
