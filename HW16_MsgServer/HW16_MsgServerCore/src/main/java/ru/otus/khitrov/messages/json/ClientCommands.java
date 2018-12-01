package ru.otus.khitrov.messages.json;

public enum ClientCommands {
    READ_ALL("read_all"),
    FIND("find"),
    ADD("add");

    private final String cmd;

    ClientCommands(String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() { return this.cmd; }
    public boolean equals(String otherName) {
        return cmd.equals(otherName);
    }


}
