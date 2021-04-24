package me.biocomp.hubitat_ci

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.AutoImplement
import groovy.transform.CompileStatic
import me.biocomp.hubitat_ci.api.Capability
import me.biocomp.hubitat_ci.api.app_api.AppExecutor
import me.biocomp.hubitat_ci.api.common_api.ChildDeviceWrapper
import me.biocomp.hubitat_ci.api.common_api.EventSubscriptionWrapper
import me.biocomp.hubitat_ci.api.common_api.Hub
import me.biocomp.hubitat_ci.api.common_api.HubAction
import me.biocomp.hubitat_ci.api.common_api.HubResponse
import me.biocomp.hubitat_ci.api.common_api.InstalledAppWrapper
import me.biocomp.hubitat_ci.api.common_api.Location
import me.biocomp.hubitat_ci.api.device_api.DeviceExecutor
import me.biocomp.hubitat_ci.api.device_api.zigbee.Zigbee
import me.biocomp.hubitat_ci.api.device_api.zwave.Zwave
import me.biocomp.hubitat_ci.api.domain.Device
import me.biocomp.hubitat_ci.app.HubitatAppSandbox
import me.biocomp.hubitat_ci.device.HubitatDeviceSandbox
import me.biocomp.hubitat_ci.util.ApiExporter
import me.biocomp.hubitat_ci.util.CapturingLog
import me.biocomp.hubitat_ci.util.PreprocessedClasses
import me.biocomp.hubitat_ci.validation.Flags
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class ApiCompatibilityTest extends Specification
{
    static final String c_basePackageName = "me.biocomp.hubitat_ci.api"
    static final List c_exportedClasses = ApiExporter.readClassesFromJson("src/test/groovy/hubitat_api.json", "src/test/groovy/hubitat_api_overrides.json").collect { it }
    static final Map c_exportedClassesAsMap = c_exportedClasses.collectEntries{[ApiExporter.renameHubitatClass(ApiExporter.readClassName(it), c_basePackageName), it]}

    def "hubitat_api_exporter_driver.groovy returns proper set of classes"() {
        when:
            final def zwave = Mock(Zwave)
            final def zigbee = Mock(Zigbee)

            final def api = Mock(DeviceExecutor) {
                1 * getZwave() >> zwave
                1 * getZigbee() >> zigbee
            }

            final def script = new HubitatDeviceSandbox(
                    new File("Scripts/Devices/hubitat_api_exporter_driver.groovy"))
                    .run(api: api)

        then:
            script.getExportedClasses().collect { it.name } == [
                    "hubitat_api_exporter_driver",
                    zwave.class.name,
                    zigbee.class.name
            ]
    }

    @Unroll
    def "hubitat_api_exporter_app.groovy dumps class #cls correctly"(Class cls, def expectedDump, def discoveredTypes) {
        when:
            final def api = Mock(AppExecutor) {
                _ * getLog() >> new CapturingLog()
            }

            final def script = new HubitatAppSandbox(
                    new File("Scripts/hubitat_api_exporter_app.groovy")).run(
                    validationFlags: Flags.DontRunScript, api: api)

            final def actualDiscoveredTypes = [:]
            final def actualDump = script.dumpClassImpl(null, cls, actualDiscoveredTypes);

            final def sortedActualDiscoveredTypes = actualDiscoveredTypes.collect { [name: it.key, obj: it.value.obj, cls: it.value.cls] }.sort { it.name }
            final def sortedExpectedDiscoveredTypes = discoveredTypes.sort { it.name }

            def expectedName = ApiExporter.readClassName(expectedDump)
            def actualName = ApiExporter.readClassName(actualDump)

        then:
            assert actualName == expectedName

            if (expectedDump.methods) {
                assert actualDump.methods.size() == expectedDump.methods.size()
                assert actualDump.methods.collect{[it.name, it.returnType, it.parameters, it.modifiers]}.sort{it[0]} as ArrayList ==
                    expectedDump.methods.collect{[it.name, it.returnType, it.parameters, it.modifiers]}.sort{it[0]} as ArrayList
            } else if (expectedDump.enum_values) {
                assert expectedDump.enum_values == actualDump.enum_values
            }

            assert sortedActualDiscoveredTypes.size() == sortedExpectedDiscoveredTypes.size()

            for (int i in 0..<sortedActualDiscoveredTypes.size()) {
                assert sortedActualDiscoveredTypes[i].name == sortedExpectedDiscoveredTypes[i].name
                assert sortedActualDiscoveredTypes[i].cls == sortedExpectedDiscoveredTypes[i].cls
                if (sortedExpectedDiscoveredTypes[i].obj != null) {
                    assert (sortedActualDiscoveredTypes[i].obj != null)
                    assert sortedActualDiscoveredTypes[i].obj.class == sortedExpectedDiscoveredTypes[i].obj.class
                } else {
                    assert sortedActualDiscoveredTypes[i].obj == null
                }
            }

        where:
            cls << [TestToDump.class, EnumToDump.class, ClassWithTrickyMethods.class]

            expectedDump << [[
                                     class_name: "me.biocomp.hubitat_ci.TestToDump",
                                     methods   : [
                                             [returnType: TestToDump, name: "TestToDump", parameters: [], modifiers: ["constructor"]],
                                             [returnType: TestToDump, name: "TestToDump", parameters: [OtherFromConstructor], modifiers: ["constructor"]],
                                             [returnType: TestToDump, name: "TestToDump", parameters: [ClassFromConstructor], modifiers: ["constructor"]],
                                             [returnType: void, name: "baz", parameters: [], modifiers: []],
                                             [returnType: void, name: "bar", parameters: [], modifiers: []],
                                             [returnType: java.lang.String, name: "bar", parameters: [java.lang.String, java.lang.String], modifiers: []],
                                             [returnType: int, name: "getFoo", parameters: [], modifiers: []],
                                             [returnType: void, name: "setFoo", parameters: [int], modifiers: []],
                                             [returnType: Class, name: "staticMethodNoArgsReturnsClass", parameters: [], modifiers: ["static"]],
                                             [returnType: Object, name: "staticMethodNoArgs", parameters: [], modifiers: ["static"]],
                                             [returnType: Object, name: "nonStaticMethodNoArgs", parameters: [], modifiers: []],
                                             [returnType: List, name: "returnsList", parameters: [], modifiers: []],
                                             [returnType: double[], name: "returnsArrayOfDouble", parameters: [], modifiers: []],
                                             [returnType: ClassFromArray1[], name: "returnsArrayAndReceivesArray", parameters: [ClassFromArray2[]], modifiers: []],
                                             [returnType: Map, name: "returnsMap", parameters: [], modifiers: []]
                                     ]],
                             [
                                     enum_name  : "me.biocomp.hubitat_ci.EnumToDump",
                                     enum_values: [EnumToDump.One, EnumToDump.Two]
                             ],
                             [
                                     class_name  : "me.biocomp.hubitat_ci.ClassWithTrickyMethods",
                                     methods: [
                                             [returnType: ClassWithTrickyMethods, name: "ClassWithTrickyMethods", parameters: [], modifiers: ["constructor"]],
                                             [returnType: void, name: "foo", parameters: [], modifiers: []],
                                             [returnType: void, name: "bar", parameters: [], modifiers: []],
                                             [returnType: void, name: "regularMethod", parameters: [], modifiers: []],
                                             [returnType: void, name: "another_regular_method", parameters: [], modifiers: []],
                                     ]
                             ],

            ]

            discoveredTypes << [
                    [
                            [name: "me.biocomp.hubitat_ci.ClassFromConstructor", obj: null, cls: ClassFromConstructor],
                            [name: "me.biocomp.hubitat_ci.OtherFromConstructor", obj: null, cls: OtherFromConstructor],
                            [name: "me.biocomp.hubitat_ci.ClassFromArray1", obj: null, cls: ClassFromArray1],
                            [name: "me.biocomp.hubitat_ci.ClassFromArray2", obj: null, cls: ClassFromArray2],
                            [name: "me.biocomp.hubitat_ci.ClassOfObjectFromList", obj: new ClassOfObjectFromList(), cls: ClassOfObjectFromList],
                            [name: "me.biocomp.hubitat_ci.ClassOfValueFromMap", obj: new ClassOfValueFromMap(), cls: ClassOfValueFromMap],
                            [name: "me.biocomp.hubitat_ci.ReturnedFromStaticMethodNoArgs", obj: new ReturnedFromStaticMethodNoArgs(), cls: ReturnedFromStaticMethodNoArgs],
                            [name: "me.biocomp.hubitat_ci.ReturnedFromNonStaticMethodNoArgs", obj: new ReturnedFromNonStaticMethodNoArgs(), cls: ReturnedFromNonStaticMethodNoArgs],
                            [name: "me.biocomp.hubitat_ci.ReturnedFromStaticMethodNoArgsAsClass", obj: null, cls: ReturnedFromStaticMethodNoArgsAsClass],
                    ],
                    [:],
                    [:]
            ]
    }

    @AutoImplement
    class MockCapability implements Capability{}

    @AutoImplement
    class MockEventSubscriptionWrapper implements EventSubscriptionWrapper {}

    @AutoImplement
    class MockHubResponse implements HubResponse {}

    def "hubitat_api_exporter_app.groovy dumps json correctly"() {
        when:
            final def hub = Mock(Hub) {
                _ * getFirmwareVersionString() >> "1.2.3"
            }

            final def location = Mock(Location) {
                _ * getHubs() >> [hub]
            }

            final def app = Mock(InstalledAppWrapper) {
                _ * getId() >> 42
            }

            final def zwave = Mock(Zwave)
            final def zigbee = Mock(Zigbee)

            final def capability = new MockCapability()

            final def device = Mock(Device) {
                _ * getDeviceNetworkId() >> "Device_exporter_42"
            }

            final def deviceApi = Mock(DeviceExecutor) {
                _ * getZwave() >> zwave
                _ * getZigbee() >> zigbee
            }

            final def deviceScript = new HubitatDeviceSandbox(
                    new File("Scripts/Devices/hubitat_api_exporter_driver.groovy"))
                    .run(api: deviceApi)

            final ChildDeviceWrapper childDevice = [
                    device: device,
                    getExportedClasses: { deviceScript.getExportedClasses() },
                    on: {},
                    off: {},
                    capabilities: [capability]
            ] as ChildDeviceWrapper

            final Map state = [accessToken: "ACCESS_TOKEN"]

            def script = null

            final def api = Mock(AppExecutor) {
                _ * getLocation() >> location
                _ * getLog() >> new CapturingLog([])
                _ * getApp() >> app
                _ * getAllChildDevices() >> [childDevice]
                _ * sendHubCommand(_) >> {
                    HubAction action ->
                        assert action.options.callback == "onHubResponse"
                        script.onHubResponse(new MockHubResponse())
                }
                _ * subscribe(childDevice, "switch.on", "onHandler") >>  {
                    d, name, callback -> script.onHandler(new MockEventSubscriptionWrapper())
                }
                _ * getFullLocalApiServerUrl() >> "server_url"
                _ * getState() >> state
            }

            script = new HubitatAppSandbox(
                    new File("Scripts/hubitat_api_exporter_app.groovy")).run(
                        api: api,
                    validationFlags: [Flags.DontRunScript, Flags.AllowAnyExistingDeviceAttributeOrCapabilityInSubscribe])

            def result = new JsonSlurper().parseText(script.dumpClassesImpl())
            def classes = result.classes

        then:
            result.firmware_version == "1.2.3"
            !classes.find { it.class_name == "hubitat_api_exporter_app" }.methods.isEmpty() //!= startsWith('[{"class_name":"hubitat_api_exporter_app"')//"[[class_name:'hubitat_api_exporter_app',methods:[]]]"
            !classes.find { it.class_name?.contains("InstalledAppWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("ChildDeviceWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("DeviceWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("DeviceWrapperList") }.methods.isEmpty()
            !classes.find { it.class_name?.contains(zwave.class.name) }.methods.isEmpty()
            !classes.find { it.class_name?.contains(zigbee.class.name) }.methods.isEmpty()
            !classes.find { it.class_name?.contains(deviceScript.class.name) }.methods.isEmpty()
            !classes.find { it.class_name == String.class.name }
            !classes.find { it.class_name?.contains("MockCapability") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("MockEventSubscriptionWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("MockHubResponse") }.methods.isEmpty()
    }

    @Ignore // Comment out Ignore to export classes from Hubitat controller
    def "Dump all classes from hubitat_api_exporter_app.groovy to file"() {
        when:
            // HUBITAT_API_EXTRACTOR_URL env variable should be URL in form:
            // http://YOUR.HUBITAT.LOCAL.IP/apps/api/APP_ID/dump_classes?access_token=ACCESS_TOKEN"
            // It should be hubitat_api_exporter_app.groovy deployed in your Hubitat hub
            def url = System.getenv("HUBITAT_API_EXTRACTOR_URL")
            assert (url)

            def get = new URL(url).openConnection()
            assert get.responseCode == 200

            def responseText = get.getInputStream().text
            JsonSlurper jsonSlurper = new JsonSlurper()
            def result = jsonSlurper.parseText(responseText)
            def classes = result.classes

            new File("src/test/groovy/hubitat_api.json").write(JsonOutput.prettyPrint(responseText))

        then:
            !classes.find { it.class_name?.contains("AppExecutor") }.methods.isEmpty() //!= startsWith('[{"class_name":"hubitat_api_exporter_app"')//"[[class_name:'hubitat_api_exporter_app',methods:[]]]"
            !classes.find { it.class_name?.contains("InstalledAppWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("ChildDeviceWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("DeviceWrapper") }.methods.isEmpty()
            !classes.find { it.class_name?.contains("DeviceWrapperList") }.methods.isEmpty()
            classes.find { it.class_name?.contains("zigbee") }.size() >= 2
            !classes.find { it.class_name == String.class.name }
    }

    @Unroll
    def "Finds nested classes properly"(List classes, HashMap expectedResult) {
        expect:
            ApiExporter.findNestedClasses(classes) == expectedResult

        where:
            classes << [
                    [
                            [
                                    "class_name": "com.hubitat.zigbee.Zigbee",
                                    "methods"   : []
                            ],
                            [
                                    "class_name": "com.hubitat.zigbee.Zigbee\$SubClass1",
                                    "methods"   : []
                            ],
                            [
                                    "class_name": "com.hubitat.zigbee.Zigbee\$SubClass1\$SubClass2",
                                    "methods"   : []
                            ],
                            [
                                    "enum_name"  : "com.hubitat.zigbee.Zigbee\$ZigbeeCluster",
                                    "enum_values": ["BASIC_CLUSTER"]
                            ],
                            [ "class_name": "hubitat.zigbee.SmartShield" ]
                    ],
                    [
                            [ "class_name": "hubitat.zigbee.SmartShield" ]
                    ]
            ]

            expectedResult << [
                    [
                            "com.hubitat.zigbee.Zigbee": ["com.hubitat.zigbee.Zigbee\$ZigbeeCluster", "com.hubitat.zigbee.Zigbee\$SubClass1"] as Set,
                            "com.hubitat.zigbee.Zigbee\$SubClass1": ["com.hubitat.zigbee.Zigbee\$SubClass1\$SubClass2"] as Set
                    ],
                    [:]
            ]
    }

    @Unroll
    def "Parse type #src -> #target"(String src, String target) {
        expect:
            ApiExporter.fixType(src, "base.unused") == target

        where:
            src                    | target
            "java.Lang.String"     | "java.Lang.String"
            "[Ljava.Lang.String;"  | "java.Lang.String[]"
            "[[Ljava.Lang.String;" | "java.Lang.String[][]"
            "[D"                   | "double[]"
            "int"                  | "int"
    }

    @Unroll
    def "Renaming class #src to #dst with root = #root"(String src, String root, String dst) {
        expect:
            ApiExporter.renameHubitatClass(src, root) == dst

        where:
            src                                                | root                 || dst
            "hubitat.zwave.Zwave"                              | "test_package.root"  || "test_package.root.device_api.zwave.Zwave"
            "hubitat.zwave.Zwave"                              | "test_package.root." || "test_package.root.device_api.zwave.Zwave"
            "hubitat.zwave.Zwave"                              | ""                   || "device_api.zwave.Zwave"
            "com.hubitat.zigbee.Zigbee"                        | "test_package.root." || "test_package.root.device_api.zigbee.Zigbee"
            "com.hubitat.zigbee.Zigbee\$ZigbeeCluster"         | "test_package.root." || "test_package.root.device_api.zigbee.Zigbee.ZigbeeCluster"
            "com.hubitat.app.DeviceWrapper"                    | "r"                  || "r.common_api.DeviceWrapper"
            "com.hubitat.hub.domain.AppType"                   | "r"                  || "r.common_api.AppType"
            "com.hubitat.hub.domain.State"                     | "r"                  || "r.State"
            "com.hubitat.hub.controller.interfaces.ChromeCast" | "r"                  || "r.common_api.ChromeCast"
            "com.hubitat.hub.domain.Hub"                       | "r"                  || "r.common_api.Hub"
            "com.hubitat.hub.domain.State"                     | ""                   || "State"
            "hubitat.hub.domain.Location"                      | ""                   || "common_api.Location"
            "hubitat.hub.domain.Mode"                          | ""                   || "common_api.Mode"
            "hubitat.app.InstalledAppWrapper"                  | ""                   || "common_api.InstalledAppWrapper"
            "hubitat.app.ChildDeviceWrapper"                   | ""                   || "common_api.ChildDeviceWrapper"
            "hubitat.hub.executor.BaseExecutor.Log"            | ""                   || "common_api.Log"
    }

    @Unroll
    def "Class #cls produces path = #path and filename = #fileName"(String cls, String path, String fileName) {
        expect:
            ApiExporter.classNameToFilePath(cls) == new Tuple(path, fileName)

        where:
            cls                         || path                | fileName
            "hubitat.zwave.Zwave"       || "device_api/zwave"  | "Zwave.groovy"
            "com.hubitat.zigbee.Zigbee" || "device_api/zigbee" | "Zigbee.groovy"
    }

    @Unroll
    def "Test class '#className' generation"(def clsDefinition, def expectedPrintout, String className) {
        when:
            StringBuilder result = new StringBuilder()

            ApiExporter.generateClass(
                    result,
                    0,
                    "test_package.root",
                    clsDefinition[0],
                    new PreprocessedClasses(
                            clsDefinition.collectEntries{[it.class_name ? it.class_name : it.enum_name, it]},
                            ApiExporter.findNestedClasses(clsDefinition)),
                    [] as Set)

        then:
            result.toString() == expectedPrintout

        where:
            clsDefinition << [
                    [
                            [
                                "class_name": "com.hubitat.zigbee.Zigbee",
                                "methods"   : [
                                        [
                                                "returnType": "java.util.List",
                                                "name"      : "batteryConfig",
                                                "parameters": [],
                                                "modifiers" : []
                                        ],
                                        [
                                                "returnType": "com.hubitat.zigbee.Zigbee\$ZigbeeCluster",
                                                "name"      : "clusterLookup",
                                                "parameters": ["java.lang.Object"],
                                                "modifiers" : ["static"]
                                        ],
                                        [
                                                "returnType": "[Lcom.hubitat.zigbee.Zigbee\$ZigbeeCluster;",
                                                "name"      : "arrayOfClusters",
                                                "parameters": [],
                                                "modifiers" : []
                                        ],
                                        [
                                                "returnType": "java.util.List",
                                                "name"      : "command",
                                                "parameters": [
                                                        "java.lang.Integer",
                                                        "java.lang.Integer",
                                                        "[Ljava.lang.String;"
                                                ],
                                                "modifiers" : []
                                        ],
                                        [
                                                "returnType": "hubitat.zigbee.clusters.iaszone.ZoneStatus",
                                                "name": "parseZoneStatus",
                                                "parameters": [
                                                        "java.lang.String"
                                                ],
                                                "modifiers": [
                                                        "static"
                                                ],
                                        ],
                                        [
                                                "returnType": "[Lhubitat.zigbee.clusters.iaszone.ZoneStatus;",
                                                "name": "arrayOfStatuses",
                                                "parameters": [],
                                                "modifiers": [],
                                        ]
                                ]
                        ],
                        [
                                "enum_name"  : "com.hubitat.zigbee.Zigbee\$ZigbeeCluster",
                                "enum_values": [
                                        "BASIC_CLUSTER",
                                        "POWER_CONFIGURATION_CLUSTER",
                                ]
                        ]
                    ],
                    [
                            [
                                    "class_name": "hubitat.zwave.Zwave",
                                    "methods"   : [
                                            [
                                                    "returnType": "hubitat.zwave.Command",
                                                    "name"      : "getCommand",
                                                    "parameters": [ "byte",
                                                                    "byte",
                                                                    "[B"],
                                                    "modifiers" : ["static"]
                                            ],
                                            [
                                                    "returnType": "java.lang.Class",
                                                    "name"      : "getChimneyFanV1", // This is going to be converted to method returning object.
                                                    "parameters": [],
                                                    "modifiers" : ["static"]
                                            ]
                                    ],
                            ]
                    ],
                    [
                            [
                                    "class_name": "hubitat.zwave.commands.associationv1.AssociationGroupingsReport",
                                    "methods": [
                                            [
                                                "returnType": "java.lang.String",
                                                "name": "format",
                                                "parameters": [],
                                                "modifiers": [],
                                            ],
                                            [
                                                    "returnType": "hubitat.zwave.commands.associationv1.AssociationGroupingsReport",
                                                    "name": "AssociationGroupingsReport",
                                                    "parameters": [],
                                                    "modifiers": ["constructor"],
                                            ]
                                    ]
                            ]
                    ],
                    [
                            [
                                "class_name": "com.hubitat.hub.domain.State",
                                "options": ["generate_class"],
                                "methods": [
                                        [
                                                "returnType": "void",
                                                "name": "foo",
                                                "parameters": []
                                        ],
                                        [
                                                "returnType": "java.lang.String",
                                                "name": "bar",
                                                "parameters": [],
                                                "modifiers": ["static"]
                                        ],
                                ]
                            ]
                    ]
                    ]

            expectedPrintout << [
                    """package test_package.root.device_api.zigbee

trait Zigbee {
    enum ZigbeeCluster {
        BASIC_CLUSTER,
        POWER_CONFIGURATION_CLUSTER,
    }
    abstract java.util.List batteryConfig()
    abstract test_package.root.device_api.zigbee.Zigbee.ZigbeeCluster clusterLookup(java.lang.Object a)
    abstract test_package.root.device_api.zigbee.Zigbee.ZigbeeCluster[] arrayOfClusters()
    abstract java.util.List command(java.lang.Integer a, java.lang.Integer b, java.lang.String[] c)
    abstract test_package.root.device_api.zigbee.clusters.iaszone.ZoneStatus parseZoneStatus(java.lang.String a)
    abstract test_package.root.device_api.zigbee.clusters.iaszone.ZoneStatus[] arrayOfStatuses()
}
""",                """package test_package.root.device_api.zwave

trait Zwave {
    abstract test_package.root.device_api.zwave.Command getCommand(byte a, byte b, byte[] c)
    abstract java.lang.Object getChimneyFanV1()
}
""",
                    """package test_package.root.device_api.zwave.commands.associationv1

trait AssociationGroupingsReport {
    abstract java.lang.String format()
}
""",
                    """package test_package.root

class State {
    void foo() { null }
    java.lang.String bar() { null }
}
"""
            ]

            className = clsDefinition[0].class_name

    }

    @Ignore // Un-ignore to (re-)generate API classes after dumping them from Hubitat controller.
    def "Generate all API classes"() {
        expect:
            ApiExporter.generateClasses("src/test/groovy/hubitat_api.json", "src/test/groovy/hubitat_api_overrides.json", "src/main/groovy/me/biocomp/hubitat_ci/api", c_basePackageName)
    }

    @Unroll
    def "compareMethods(#m1, #m2) -> #res test"(def m1, def m2, int res) {
        expect:
            ApiExporter.compareMethods(m1, m2) == res

        where:
            m1                                                                              | m2                                                                              || res
            [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  | [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  || 0
            [name: 'm', returnType: 'void', parameters: [boolean.name], modifiers: []]      | [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  || 1 <=> 0
            [name: 'm', returnType: 'void', parameters: []]                                 | [name: 'm', returnType: 'void', parameters: [Boolean.name]]                     || 0 <=> 1
            [name: 'm', returnType: 'void', parameters: [boolean.name]]                     | [name: 'm', returnType: 'void', parameters: [Boolean.name]]                     || 0
            [name: 'm', returnType: Integer.name, parameters: [boolean.name]]               | [name: 'm', returnType: int.name, parameters: [Boolean.name]]                   || 0
            [name: 'm1', returnType: 'void', parameters: []]                                | [name: 'm2', returnType: 'void', parameters: []]                                || 'm1' <=> 'm2'
            [name: 'm', returnType: 'boolean', parameters: [], modifiers: []]               | [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  || 'boolean' <=> 'void'
            [name: 'm', returnType: 'void', parameters: ['int'], modifiers: []]             | [name: 'm', returnType: 'void', parameters: ['double'], modifiers: []]          || 'int' <=> 'double'
            [name: 'm', returnType: 'void', parameters: [], modifiers: ['static']]          | [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  || 1 <=> 0
            [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  | [name: 'm', returnType: 'void', parameters: [], modifiers: ['static']]          || 0 <=> 1
            [name: 'm', returnType: Object.class.name, parameters: [], modifiers: []]       | [name: 'm', returnType: String.class.name, parameters: [], modifiers: []]       || 0
            [name: 'm', returnType: String.class.name, parameters: [], modifiers: []]       | [name: 'm', returnType: Object.class.name, parameters: [], modifiers: []]       || 0
            [name: 'm', returnType: 'void', parameters: [], modifiers: []]                  | [name: 'm', returnType: Object.class.name, parameters: [], modifiers: []]       || 'void' <=> Object.class.name
            [name: 'm', returnType: 'void', parameters: [Object.class.name], modifiers: []] | [name: 'm', returnType: 'void', parameters: [String.class.name], modifiers: []] || 0
    }

    @CompileStatic
    static private String doesClassExist(String className) {
        try {
            Class.forName(ApiExporter.renameHubitatClass(className, c_basePackageName))
        } catch (ClassNotFoundException) {
            return "--- does not exist ---"
        }

        return "+ exists +"
    }

    @Unroll
    def "Verify local API class #clsName is exported"() {
        expect:
            assert cls.name in c_exportedClassesAsMap : "Class ${clsName} not exported"

        where:
            cls << ApiExporter.findExportedClasses("src/main/groovy/me/biocomp/hubitat_ci/api", "me.biocomp.hubitat_ci.api")
            clsName = cls.name
    }

    def "Basic validations for hubitat_api.json"() {
        when:
            def numberOfClasses = [:]
            c_exportedClasses.each{
                String className = ApiExporter.readClassName(it)
                if (numberOfClasses.containsKey(className)) {
                    numberOfClasses[className]++;
                } else {
                    numberOfClasses[className] = 1;
                }
            }

            def duplicates = []
            numberOfClasses.each{
                if (it.value > 1) {
                    duplicates << [it.key, it.value]
                }
            }

        then:
            assert duplicates.size() == 0 : duplicates.collect{ "Class ${it[0]} has ${it[1]} copies in json" }.join("\n")
    }

    @Unroll
    def "Verify exported API class #clsName (#exists)"() {
        when:
            def localClassName = ApiExporter.renameHubitatClass(clsName, c_basePackageName, false)

        then:
            def cls = Class.forName(localClassName)

            // It's a class
            if (classDefinition.class_name) {
                assert !cls.isEnum()

                boolean generateClass = !(classDefinition.options?.findAll{it == "generate_class"} == null)

                def ignoreConstructor = { generateClass ? true : !("constructor" in it.modifiers) }

                Set methodsToIgnore = (classDefinition.ignore_methods ? classDefinition.ignore_methods.collect{it.name} : []) as Set
                final def ignoreMethod = {ignoreConstructor(it) && !(it.name in methodsToIgnore)}

                def actualMethods = ApiExporter.readMethodsFromClass(cls).findAll(ignoreMethod).collect{ApiExporter.convertMethodToLocalTypes(it, c_basePackageName)}
                def expectedMethods = classDefinition.methods.findAll(ignoreMethod).collect{ApiExporter.convertMethodToLocalTypes(it, c_basePackageName)}

                def disjunction = {l1, l2 -> return l1.findAll{m1 -> l2.find{m2 -> ApiExporter.compareMethods(m1, m2) == 0} == null}}

                def methodsToRemove = disjunction(actualMethods, expectedMethods)
                def methodsToAdd = disjunction(expectedMethods, actualMethods)

                if (!generateClass) {
                    assert cls.isInterface()
                } else {
                    assert !cls.isInterface()
                }

                assert methodsToRemove.size() == 0 && methodsToAdd.size() == 0:
                        "Methods don't match, here's the diff:\n" +
                                "-- Methods to remove:\n" +
                                "${methodsToRemove.collect{ApiExporter.printMethodDeclaration(!generateClass, it, c_basePackageName) + " // Original: ${it.as_string}"}.join('\n')}\n\n" +
                                "++ Methods to add:\n" +
                                "${methodsToAdd.collect{ApiExporter.printMethodDeclaration(!generateClass, it, c_basePackageName)+ " // Original: ${it.as_string}"}.join('\n')}"
            } else { // It's an enum
                assert cls.isEnum()
            }
        where:
            classDefinition << c_exportedClasses
            clsName = ApiExporter.readClassName(classDefinition)
            exists = doesClassExist(clsName)
    }
}

trait TraitA {
    void baz() {}
}

trait TraitB {
    void bar() {}
}

class BaseToDump {
    String toString() {
        return null
    }

    List returnsList() {
        return [new ClassOfObjectFromList()]
    }

    ClassFromArray1[] returnsArrayAndReceivesArray(ClassFromArray2[] a) { return null }

    def methodMissing(String name, def args) {
    }

    def propertyMissing(String name) {
    }

    void setProperty(String name, Object value) {
    }

    def getProperty(String name) {
    }
}

class ReturnedFromStaticMethodNoArgs {
}

class ReturnedFromStaticMethodNoArgsAsClass {
}

class ReturnedFromNonStaticMethodNoArgs {
}

class ClassOfObjectFromList {
}

class ClassOfValueFromMap {
}

class ClassFromArray1 {
}

class ClassFromArray2 {
}

class ClassFromConstructor {
}

class OtherFromConstructor {
}

class TestToDump extends BaseToDump implements TraitA, TraitB, Cloneable {
    int foo

    TestToDump() {}
    TestToDump(ClassFromConstructor a) {}
    TestToDump(OtherFromConstructor a) {}

    String bar(String a, String b) {
    }

    @Override
    String toString() {
        return null
    }

    Object clone() throws CloneNotSupportedException // We'll filter this method out
    {
        return super.clone();
    }

    static Class staticMethodNoArgsReturnsClass() {
        return ReturnedFromStaticMethodNoArgsAsClass.class
    }

    static Object staticMethodNoArgs() {
        return new ReturnedFromStaticMethodNoArgs()
    }

    Object nonStaticMethodNoArgs() {
        return new ReturnedFromNonStaticMethodNoArgs()
    }

    Map returnsMap() {
        return [key: new ClassOfValueFromMap()]
    }

    def methodMissing(String name, def args) {
    }

    def propertyMissing(String name) {
    }

    void setProperty(String name, Object value) {
    }

    def getProperty(String name) {
    }

    double[] returnsArrayOfDouble() { null }

    private void privateMethodShouldNotBeReported() {}
}

enum EnumToDump {
    One,
    Two
}

trait Base1 { void foo() {} }
trait Base2 extends Base1 { void bar() {} }

class ClassWithTrickyMethods implements Base1,  Base2 {
    void _hiddenMethod() {} // Should be ignored when dumping because of leading '_'
    static void _static_hidden_method() {} // Should be ignored when dumping because of leading '_'
    void regularMethod() {}
    void another_regular_method() {}


    boolean canEqual(java.lang.Object a) {} // Explicitly ignored
    boolean equals(java.lang.Object a) {} // Explicitly ignored
    int hashCode() {} // Explicitly ignored
}