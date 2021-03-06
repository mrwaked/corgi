package cz.wake.corgibot.commands.admin;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cz.wake.corgibot.CorgiBot;
import cz.wake.corgibot.annotations.SinceCorgi;
import cz.wake.corgibot.commands.Command;
import cz.wake.corgibot.commands.CommandCategory;
import cz.wake.corgibot.objects.GuildWrapper;
import cz.wake.corgibot.utils.Constants;
import cz.wake.corgibot.utils.MessageUtils;
import cz.wake.corgibot.utils.lang.I18n;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

@SinceCorgi(version = "1.2.0")
public class SetPrefix implements Command {

    @Override
    public void onCommand(MessageChannel channel, Message message, String[] args, Member member, EventWaiter w, GuildWrapper gw) {
        if (args.length == 1) {
            if (CorgiBot.isIsBeta()) {
                channel.sendMessage(I18n.getLoc(gw, "commands.prefix.corgi-is-beta-prefix")).queue();
                return;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                gw.setPrefix("c!", true);
                channel.sendMessage(MessageUtils.getEmbed(Constants.GREEN).setDescription(I18n.getLoc(gw, "commands.prefix.reset-back")).build()).queue();
            } else if (args[0].length() < 4) {
                gw.setPrefix(args[0], true);
                channel.sendMessage(MessageUtils.getEmbed(Constants.GREEN).setDescription(String.format(I18n.getLoc(gw, "commands.prefix.prefix-sets-to"), args[0])).build()).queue();
            } else {
                MessageUtils.sendErrorMessage(I18n.getLoc(gw, "commands.prefix.cannot-three-chars"), channel);
            }
        } else {
            channel.sendMessage(MessageUtils.getEmbed(Constants.DEFAULT_PURPLE).setDescription(String.format(I18n.getLoc(gw, "commands.prefix.actual-prefix-is"), gw.getPrefix())).build()).queue();
        }
    }

    @Override
    public String getCommand() {
        return "prefix";
    }

    @Override
    public String getDescription() {
        return "Nastaven?? vlastn??ho prefixu pro server.";
    }

    @Override
    public String getHelp() {
        return "%prefix reset/[prefix]";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.ADMINISTARTOR;
    }

    @Override
    public String[] getAliases() {
        return new String[]{"setprefix"};
    }

    @Override
    public Permission[] userPermission() {
        return new Permission[]{Permission.MANAGE_SERVER};
    }
}
