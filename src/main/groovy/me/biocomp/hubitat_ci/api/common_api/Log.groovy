package me.biocomp.hubitat_ci.api.common_api;

/**
 * Emulation of Hub's log.
 * In this api it's injected via BaseExecutor,
 * although in real code it comes from somewhere else.
 */
interface Log
{
    void info(String record)
    void debug(String record)
    void error(String record)
}

