package kelvin.fiveminsurvival.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import kelvin.fiveminsurvival.game.world.WorldStateHolder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.TimeArgument;
import net.minecraft.command.impl.TimeCommand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

public class CustomTimeCommand {

	
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
	      dispatcher.register(Commands.literal("time").requires((source) -> 
	      {
	         return source.hasPermissionLevel(2);
	      }).then(Commands.literal("set").then(Commands.literal("day").executes((source) -> 
	      {
	    	  return setTime(source.getSource(), 1000);
	      })).then(Commands.literal("noon").executes((source) -> 
	      {
	         return setTime(source.getSource(), 6000);
	      })).then(Commands.literal("night").executes((source) -> 
	      {
	         return setTime(source.getSource(), 13000);
	      })).then(Commands.literal("midnight").executes((source) -> 
	      {
	         return setTime(source.getSource(), 18000);
	      })).then(Commands.argument("time", TimeArgument.func_218091_a()).executes((source) -> 
	      {
	         return setTime(source.getSource(), IntegerArgumentType.getInteger(source, "time"));
	      }))).then(Commands.literal("add").then(Commands.argument("time", TimeArgument.func_218091_a()).executes((source) -> 
	      {
	         return addTime(source.getSource(), IntegerArgumentType.getInteger(source, "time"));
	      }))).then(Commands.literal("query").then(Commands.literal("daytime").executes((source) -> 
	      {
	         return sendQueryResults(source.getSource(), getDayTime(source.getSource().getWorld()));
	      })).then(Commands.literal("gametime").executes((source) -> 
	      {
	         return sendQueryResults(source.getSource(), (int)(source.getSource().getWorld().getGameTime() % 2147483647L));
	      })).then(Commands.literal("day").executes((source) -> 
	      {
	         return sendQueryResults(source.getSource(), (int)(source.getSource().getWorld().getDayTime() / 24000L % 2147483647L));
	      }))));
	   }
	
	private static int getDayTime(ServerWorld worldIn) {
	      return (int)(worldIn.getDayTime() % 24000L);
	}
	
	private static int sendQueryResults(CommandSource source, int time) {
	      source.sendFeedback(new TranslationTextComponent("commands.time.query", time), false);
	      return time;
	}
	
	public static int addTime(CommandSource source, int time) {
		for(ServerWorld serverworld : source.getServer().getWorlds()) {
	         //serverworld.setDayTime((long)time);
	    	  WorldStateHolder.get(serverworld).worldState.time += (long)time;
	      }
	      
	      source.sendFeedback(new TranslationTextComponent("commands.time.set", WorldStateHolder.get(source.getWorld()).worldState), true);
	      return getDayTime(source.getWorld());
	}
	
	public static int setTime(CommandSource source, int time) {
	      for(ServerWorld serverworld : source.getServer().getWorlds()) {
	         //serverworld.setDayTime((long)time);
	    	  WorldStateHolder.get(serverworld).worldState.time = (long)time;
	      }
	      
	      source.sendFeedback(new TranslationTextComponent("commands.time.set", time), true);
	      return getDayTime(source.getWorld());
	   }
	
}
