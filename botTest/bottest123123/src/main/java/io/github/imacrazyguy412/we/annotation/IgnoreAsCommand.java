package io.github.imacrazyguy412.we.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.imacrazyguy412.we.arefarmers.listeners.CommandManager;

/**
 * Indicates to the {@link CommandManager} that the annotated class
 * should not be made into its own command, such as for
 * {@link AbstractCommand} and {@link Command}.
 * 
 * 
 * @author Matthew "Something Inconspicuous"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IgnoreAsCommand {}
