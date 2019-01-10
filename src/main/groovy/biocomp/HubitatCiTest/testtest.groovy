package biocomp.hubitatCiTest

def foo()
{
    def LoginCheck = 2
}

def bar()
{
    LoginCheck = 1
    println LoginCheck

    foo()

    println LoginCheck
}

bar()
bar()