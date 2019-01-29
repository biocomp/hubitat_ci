private def myPrivateMethod1()
{
    return "original1!"
}

def publicMethodThatCallsPrivateMethod1()
{
    return myPrivateMethod1()
}

private def myPrivateMethod2(def a1, def a2)
{
    return "original2!"
}

def publicMethodThatCallsPrivateMethod2()
{
    return myPrivateMethod2(123, "argFromPublicMethod")
}