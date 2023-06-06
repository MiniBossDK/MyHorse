package com.hiphurra.myhorse.commands;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.contexts.ContextResolver;

public abstract class CommandContext {


    private final Integer thisValue;

    CommandContext(Integer thisValue) {
        this.thisValue = thisValue;
    }

    public Integer getValue() {
        return this.thisValue;
    }

    public static ContextResolver<CommandContext, BukkitCommandExecutionContext> getContextResolver() {
        return (c) -> {
            String first = c.popFirstArg();
            if ("1".equals(first)) {
                return new Test1();
            } else if ("2".equals(first)) {
                return new Test2();
            } else {
                try {
                    return new TestOther(Integer.parseInt(first));
                } catch (NumberFormatException ignored) {
                    // User didn't type a number, show error!
                    throw new InvalidCommandArgument(MessageKeys.UNKNOWN_COMMAND);
                }
            }
        };
    }

    public static class Test1 extends CommandContext {
        Test1() {
            super(1);
        }
    }
    public static class Test2 extends CommandContext {
        Test2() {
            super(2);
        }
    }
    public static class TestOther extends CommandContext {
        TestOther(Integer other) {
            super(other);
        }
    }

}
