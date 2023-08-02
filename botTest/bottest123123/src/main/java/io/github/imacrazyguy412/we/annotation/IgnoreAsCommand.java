package io.github.imacrazyguy412.we.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;
import io.github.imacrazyguy412.we.arefarmers.listeners.commands.AbstractCommand;

/**
 * Indicates to the {@link CommandManager} that the annotated class
 * should not be made into its own command, such as for
 * {@link AbstractCommand} and {@link Command}.
 * 
 * @deprecated It is now possible to exclude files from the text file
 * that specifies the commands, so this is no longer necessary
 * 
 * @author Matthew "Something Inconspicuous"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Deprecated
public @interface IgnoreAsCommand {}
