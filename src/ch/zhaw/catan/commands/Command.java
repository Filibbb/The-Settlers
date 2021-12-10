package ch.zhaw.catan.commands;

/**
 * Interface describing what methods a command should contain
 *
 * @author abuechi
 */
public interface Command {
    /**
     * the execute method that every command must have in order to execute its functionality
     */
    void execute();
}
