package me.cbitler.raidbot.edit;

import java.util.List;

import me.cbitler.raidbot.raids.Raid;
import me.cbitler.raidbot.raids.RaidManager;
import me.cbitler.raidbot.raids.RaidRole;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

/**
 * Rename a role for the event
 * @author Franziska Mueller
 */
public class RenameRoleStep implements EditStep {

	private String messageID;
	private int roleID;
	
	public RenameRoleStep(String messageId) {
		this.messageID = messageId;
		this.roleID = -1;
	}
	
    /**
     * Handle renaming an existing role
     * @param e The direct message event
     * @return True if role is renamed, false otherwise
     */
    public boolean handleDM(PrivateMessageReceivedEvent e) {
    	boolean valid = true;
    	Raid raid = RaidManager.getRaid(messageID);
    	List<RaidRole> roles = raid.getRoles();
    	if (roleID == -1) { // no role chosen yet
    		// try to parse an integer
    		try {
    			int choiceId = Integer.parseInt(e.getMessage().getRawContent()) - 1;
    			if (choiceId >= 0 && choiceId < roles.size())
    				roleID = choiceId;
    			else
    				valid = false;
    		} catch (Exception excp) {
    			valid = false;
    		}
    		if (valid == false)
        		e.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Invalid choice. Try again.").queue());
    	}
    	else { // message contains new name
    		if (raid.renameRole(roleID, e.getMessage().getRawContent())) {
    			e.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Successfully renamed role.").queue());
    			raid.updateMessage();
    		}
    		else
    			e.getAuthor().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Role could not be updated in database.").queue());	
    	}

    	return valid;
    }

    /**
     * {@inheritDoc}
     */
    public String getStepText() {
        String stepText;
        List<RaidRole> roles = RaidManager.getRaid(messageID).getRoles();
        if (roleID == -1) { // no role chosen yet
        	stepText = "Which role do you want to rename? \n";
        	for (int r = 0; r < roles.size(); r++)
        		stepText += "`" + (r+1) + "` " + roles.get(r).getName() + " \n";
        }
        else
        	stepText = "Enter a new name for the role *" + roles.get(roleID).getName() + "*:";
        
        return stepText;
    }

    /**
     * {@inheritDoc}
     */
    public EditStep getNextStep() {
        return new EditIdleStep(messageID);
    }

	@Override
	public String getMessageID() {
		return messageID;
	}
}
