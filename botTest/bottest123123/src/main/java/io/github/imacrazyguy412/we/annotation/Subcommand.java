package io.github.imacrazyguy412.we.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.Command;

/**
 * Indicates to the {@link CommandManager} that a command class
 * is a subcommand to a different one.
 * 
 * @author Matthew "Something Inconspicuous"
 * @param value The class of this command's supercommand
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subcommand {
    Class<? extends Command> value();
}
