/**
 * Created by miles on 5/20/17.
 */
public abstract class Commands {
    //caller receives a command as a string
    //all strings are formatted as such: command_type is a single word with no spaces,
    //followed by 0 or more parameters
    //gives the string to Commands
    //Commands parses the string and returns the command type
    //caller decides whether that command can be used in that situation
    //if so, asks Commands to execute the commands
}
