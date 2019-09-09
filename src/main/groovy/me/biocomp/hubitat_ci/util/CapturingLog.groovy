package me.biocomp.hubitat_ci.util

import me.biocomp.hubitat_ci.api.common_api.Log

class CapturingLog implements Log
{
    enum Level
    {
        debug,
        info,
        warn,
        error
    }

    @Override
    void info(String record) {
        records << new Tuple(Level.info, record)
    }

    @Override
    void debug(String record) {
        records << new Tuple(Level.debug, record)
    }

    @Override
    void warn(String record) {
        records << new Tuple(Level.warn, record)
    }

    @Override
    void error(String record) {
        records << new Tuple(Level.error, record)
    }

    ArrayList<Tuple> records = new ArrayList<Tuple>()
}


