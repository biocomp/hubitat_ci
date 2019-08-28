package me.biocomp.hubitat_ci.util

import biocomp.hubitatCi.api.commonApi.Log

class CapturingLog implements Log
{
    enum Level
    {
        debug,
        info,
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
    void error(String record) {
        records << new Tuple(Level.error, record)
    }

    ArrayList<Tuple> records = new ArrayList<Tuple>()
}


