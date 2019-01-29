package biocomp.hubitatCi.emulation.commonApi;

/**
 * Emulation of Hub's log.
 * In this emulation it's injected via BaseExecutor,
 * although in real code it comes from somewhere else.
 */
interface Log
{
    void info(String record)
    void debug(String record)
}

