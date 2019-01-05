package biocomp.hubitatCiTest.util

/**
 * Emulation of Hub's log.
 * In this emulation it's injected via BaseExecutorApi,
 * although in real code it comes from somewhere else.
 */
interface Log
{
    void info(String record)
    void debug(String record)
}

