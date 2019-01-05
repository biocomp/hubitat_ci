package biocomp.hubitatCiTest.util

class CapturingLog implements Log
{
    enum Level
    {
        debug,
        info
    }

    @Override
    void info(String record) {
        records << new Tuple(Level.info, record)
    }

    @Override
    void debug(String record) {
        records << new Tuple(Level.debug, record)
    }

    ArrayList<Tuple> records = new ArrayList<Tuple>()
}


