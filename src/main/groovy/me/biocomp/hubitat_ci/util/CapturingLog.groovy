package me.biocomp.hubitat_ci.util

import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.common_api.Log

class CapturingLog implements Log
{
    enum Options {
        Capture,
        LogToOutput
    }

    CapturingLog(List<Options> options = [Options.Capture]) {
        this.options = options as HashSet
        this.records = new ArrayList<Tuple>()
    }

    enum Level
    {
        debug,
        info,
        warn,
        error,
        trace
    }

    @Override
    void info(String record) {
        handleEvent(Level.info, record)
    }

    @Override
    void debug(String record) {
        handleEvent(Level.debug, record)
    }

    @Override
    void warn(String record) {
        handleEvent(Level.warn, record)
    }

    @Override
    void error(String record) {
        handleEvent(Level.error, record)
    }

    @Override
    void trace(String record) {
        handleEvent(Level.trace, record)
    }

    @CompileStatic
    private handleEvent(Level level, String record) {
        if (Options.LogToOutput in options) {
            println("${level}: " + record)
        }

        if (Options.Capture in options) {
            records << new Tuple(level, record)
        }
    }

    ArrayList<Tuple> records
    private Set<Options> options
}


