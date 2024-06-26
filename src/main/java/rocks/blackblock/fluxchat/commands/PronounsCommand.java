package rocks.blackblock.fluxchat.commands;

import com.google.gson.JsonObject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import rocks.blackblock.fluxchat.FluxChatPlayer;
import rocks.blackblock.fluxchat.FluxChatPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;

public class PronounsCommand implements SimpleCommand {

    public List<String> getPronouns() {

        List<String> result = new ArrayList<>();

        result.add("She/Her/Hers");
        result.add("He/Him/His");
        result.add("They/Them/Theirs");

        return result;
    }

    @Override
    public void execute(Invocation invocation) {

        CommandSource source = invocation.source();

        if (!(source instanceof Player player)) {
            return;
        }

        // Get the arguments after the command alias
        String[] args = invocation.arguments();

        if (args.length == 0) {
            return;
        }

        String pronouns = args[0];

        if (!this.getPronouns().contains(pronouns)) {
            source.sendMessage(Component.text("Please contact Skerit to set your custom pronouns!").color(NamedTextColor.RED));
            return;
        }

        FluxChatPlayer fluxChatPlayer = FluxChatPlayer.get(player);
        fluxChatPlayer.setPronouns(pronouns);

        if (FluxChatPlugin.shouldPushEvents()) {
            JsonObject object = FluxChatPlugin.createObject("pronouns", player);
            object.addProperty("pronouns", pronouns);
            FluxChatPlugin.pushEvent(object);
        }

        source.sendMessage(Component.text("Your pronouns have been set to " + pronouns).color(NamedTextColor.AQUA));
    }

    public List<String> suggest(final SimpleCommand.Invocation invocation) {
        return this.getPronouns();
    }
}
