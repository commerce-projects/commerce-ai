package org.simplecommerce.ai.commerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import org.simplecommerce.ai.commerce.command.ProviderMixin;
import org.simplecommerce.ai.commerce.command.TopCommand;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;
import picocli.shell.jline3.PicocliCommands;

/**
 * @author Julius Krah
 */
@Component
public class CliInitializer implements CommandLineRunner, ExitCodeGenerator {
    private final IFactory factory;
    private final TopCommand command;
    private int exitCode;

    public CliInitializer(IFactory factory, TopCommand command) {
        this.factory = factory;
        this.command = command;
    }

    @Override
    public void run(String... args) throws Exception {
        var commandsFactory = new PicocliCommands.PicocliCommandsFactory(factory);
        var cmd = new CommandLine(command, commandsFactory);
        exitCode = cmd.setExecutionStrategy(ProviderMixin::executionStrategy).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
    
}
