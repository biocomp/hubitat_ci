.. _capabilities_taxonomy:

Capabilities Reference
======================

.. important::

    The Capabilities in this document are supported in the SmartThings Classic mobile app.
    Visit the `SmartThings Developer Portal <https://smartthings.developer.samsung.com/develop/api-ref/capabilities.html>`_ for the Capabilities supported in the new SmartThings app.

Introduction
------------

Capabilities are core to the SmartThings architecture. They allow us to abstract specific devices into their underlying capabilities.
An application interacts with devices based on their capabilities, so once we understand the capabilities that are needed by a SmartApp, and the capabilities that are provided by a device, we can understand which devices (based on the Device’s declared capabilities) are eligible for use within a specific SmartApp.
Capabilities themselves are decomposed into both Commands and Attributes. Commands represent ways in which you can control or actuate the device, whereas Attributes represent state information or properties of the device.
Capabilities are created and maintained by the SmartThings development team.
This page serves as a reference for the supported capabilities.

Data Types
----------

Before we present the Capabilities, it’s worth covering the various data types associated with Attributes and Commands. Note that these data types are guidelines as to how actual values can be represented. In most cases, the SmartThings platform contains the implementation logic and defines the actual objects for these data types. Below is a table outlining the the possible data types and what they mean.

============== ============================= ======================================
Data Type      Example                       Description
============== ============================= ======================================
STRING         "This is a String"            Represents character strings
NUMBER         ``5``, ``10.67``              The Number data type is a guideline indicating that a number should be expected, and not a specific type. Device Handlers contain the implementation of what kind of number object is actually returned.
VECTOR3        ``(x,y,z)``                   This Data Type is a representation of x,y,z coordinates in space. Device Handlers contain the implementation of the actual data structure, but it is usually as a Map: ``[x: 0, y: 0, z: 0]``.
ENUM           "one", "two", "three"         The Enum Data Type is a static set of predefined String values that an Attribute can have, or that a Command can accept as an argument.
DYNAMIC_ENUM   "Any", "value"                Much like the Enum Data Type, Dynamic Enum is a set of String values. However, the set is not static or predefined.
COLOR_MAP      ``[hue: 50, saturation: 75]`` The Color Map is a Map specifically for the use of color control. As such, the Map should contain a Hue and a Saturation value.
JSON_OBJECT                                  A standard JSON object. Device Handlers contain the implementation and thus should be consulted when looking for the JSON object structure.
DATE                                         A Date, usually represented as a java.util.Date object.
BOOLEAN        true, false                   A boolean data type with a value of true or false.
============== ============================= ======================================

Acceleration Sensor
-------------------

The Acceleration Sensor capability allows for acceleration detection.
Some use cases for SmartApps using this capability would be detecting if
a washing machine is vibrating, or if a case has moved (particularly
useful for knowing if a weapon case has been moved).

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Acceleration Sensor
    status: live
    attributes:
      acceleration:
        schema:
          type: object
          properties:
            value:
              $ref: ActivityState
          required: ["value"]
        type: ENUM
        values:
          - active
          - inactive
    commands: {
      }
    public: true
    id: accelerationSensor
    ocfResourceType: x.com.st.acceleration
    version: 1

Actuator
--------

The Actuator capability is a "tagging" capability. It defines no
attributes or commands. In SmartThings terms, it represents that a
Device has commands.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-01
    name: Actuator
    status: deprecated
    attributes: {
      }
    commands: {
      }
    public: true
    id: actuator
    version: 1

Air Conditioner Mode
--------------------

Allows for the control of the air conditioner.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Air Conditioner Mode
    status: proposed
    attributes:
      airConditionerMode:
        schema:
          type: object
          properties:
            value:
              $ref: HvacMode
          required: ["value"]
        type: ENUM
        values:
          - auto
          - cool
          - dry
          - coolClean
          - dryClean
          - fanOnly
          - heat
          - heatClean
          - notSupported
        setter: setAirConditionerMode
    commands:
      setAirConditionerMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: HvacMode
          type: ENUM
          values:
            - auto
            - cool
            - dry
            - coolClean
            - dryClean
            - fanOnly
            - heat
            - heatClean
            - notSupported
    public: true
    id: airConditionerMode
    ocfResourceType: x.com.st.mode.airconditioner
    version: 1

Air Quality Sensor
------------------

Gets the air quality number.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Air Quality Sensor
    status: proposed
    attributes:
      airQuality:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
    commands: {
      }
    public: true
    id: airQualitySensor
    ocfResourceType: x.com.st.airqualitylevel
    version: 1

Alarm
-----

The Alarm capability allows for interacting with devices that serve as
alarms

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Alarm
    status: live
    attributes:
      alarm:
        schema:
          type: object
          properties:
            value:
              $ref: AlertState
          required: ["value"]
        type: ENUM
        values:
          - both
          - 'off'
          - siren
          - strobe
        enumCommands:
          - command: both
            value: both
          - command: 'off'
            value: 'off'
          - command: siren
            value: siren
          - command: strobe
            value: strobe
    commands:
      both:
        arguments: [
          ]
      'off':
        arguments: [
          ]
      siren:
        arguments: [
          ]
      strobe:
        arguments: [
          ]
    public: true
    id: alarm
    ocfResourceType: x.com.st.alarm
    version: 1

Audio Mute
----------

Allows for the control of audio mute.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-01
    name: Audio Mute
    status: live
    attributes:
      mute:
        schema:
          type: object
          properties:
            value:
              $ref: MuteState
          required:
            - value
        type: ENUM
        values:
          - muted
          - unmuted
        setter: setMute
        enumCommands:
          - command: mute
            value: muted
          - command: unmute
            value: unmuted
    commands:
      setMute:
        arguments:
        - name: state
          required: true
          schema:
            $ref: MuteState
          type: ENUM
          values:
            - muted
            - unmuted
      mute:
        arguments: [
          ]
      unmute:
        arguments: [
          ]
    public: true
    id: audioMute
    version: 1

Audio Notification
------------------

Play a track or a message as an audio notification

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Audio Notification
    status: proposed
    attributes: {
      }
    commands:
      playTrack:
        arguments:
        - name: uri
          required: true
          schema:
            $ref: URI
          type: STRING
        - name: level
          schema:
            $ref: IntegerPercent
          type: NUMBER
          required: false
      playTrackAndResume:
        arguments:
        - name: uri
          required: true
          schema:
            $ref: URI
          type: STRING
        - name: level
          schema:
            type: integer
            minimum: 0
            maximum: 100
          type: NUMBER
          required: false
      playTrackAndRestore:
        arguments:
        - name: uri
          required: true
          schema:
            $ref: URI
          type: STRING
        - name: level
          schema:
            type: integer
            minimum: 0
            maximum: 100
          type: NUMBER
          required: false
    public: true
    id: audioNotification
    ocfResourceType: x.com.st.audionotification
    version: 1

Audio Track Data
----------------

Gets the value of the audio track data.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-01
    name: Audio Track Data
    status: proposed
    attributes:
      audioTrackData:
        schema:
          type: object
          properties:
            value:
              $ref: AudioTrackAddress
          required:
            - value
        type: JSON_OBJECT
    commands: {
      }
    public: true
    id: audioTrackData
    ocfResourceType: x.com.st.audiotrackdata
    version: 1

Audio Volume
------------

Allows for the control of audio volume.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Audio Volume
    status: proposed
    attributes:
      volume:
        schema:
          $ref: IntegerPercent
        type: NUMBER
        setter: setVolume
        actedOnBy:
          - volumeUp
          - volumeDown
    commands:
      setVolume:
        arguments:
        - name: volume
          required: true
          schema:
            type: integer
            minimum: 0
            maximum: 100
          type: NUMBER
      volumeUp:
        arguments: [
          ]
      volumeDown:
        arguments: [
          ]
    public: true
    id: audioVolume
    ocfResourceType: x.com.st.audiovolume
    version: 1

Battery
-------

Defines that the device has a battery

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-03
    name: Battery
    status: live
    attributes:
      battery:
        schema:
          $ref: IntegerPercent
        type: NUMBER
        unit: '%'
    commands: {
      }
    public: true
    id: battery
    ocfResourceType: oic.r.energy.battery
    version: 1

Beacon
------

Detect whether or not the beacon is present

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-01
    name: Beacon
    status: deprecated
    attributes:
      presence:
        schema:
          type: object
          properties:
            value:
              $ref: PresenceState
          required:
            - value
        type: ENUM
        values:
          - not present
          - present
    commands: {
      }
    public: true
    id: beacon
    version: 1

Bridge
------

The Bridge capability is a "tagging" capability. It defines no
attributes or commands. In SmartThings terms, it represents that a
Device is a bridge to other devices.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-01
    name: Bridge
    status: deprecated
    attributes: {
      }
    commands: {
      }
    public: true
    id: bridge
    version: 1

Bulb
----

Allows for the control of a bulb device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Bulb
    status: dead
    attributes:
      switch:
        schema:
          type: object
          properties:
            value:
              $ref: SwitchState
          required: ["value"]
        type: ENUM
        values:
          - 'off'
          - 'on'
        enumCommands:
          - command: 'off'
            value: 'off'
          - command: 'on'
            value: 'on'
    commands:
      'off':
        arguments: [
          ]
      'on':
        arguments: [
          ]
    public: true
    id: bulb
    version: 1

Button
------

A device with one or more buttons

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Button
    status: deprecated
    attributes:
      button:
        schema:
          type: object
          properties:
            value:
              $ref: ButtonState
          required:
            - value
        type: ENUM
        values:
          - held
          - pushed
      numberOfButtons:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: button
    ocfResourceType: x.com.st.button
    version: 1

Carbon Dioxide Measurement
--------------------------

Measure carbon dioxide levels

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Carbon Dioxide Measurement
    status: live
    attributes:
      carbonDioxide:
        schema:
          type: object
          properties:
            value:
              type: integer
              minimum: 0
              maximum: 1000000
            unit:
              type: string
              enum:
                - ppm
              default:
                - ppm
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: carbonDioxideMeasurement
    version: 1

Carbon Monoxide Detector
------------------------

Measure carbon monoxide levels

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Carbon Monoxide Detector
    status: live
    attributes:
      carbonMonoxide:
        schema:
          type: object
          properties:
            value:
              $ref: CarbonMonoxideState
          required: ["value"]
        type: ENUM
        values:
          - clear
          - detected
          - tested
    commands: {
      }
    public: true
    id: carbonMonoxideDetector
    ocfResourceType: x.com.st.carbonmonoxidedetector
    version: 1

Color Control
-------------

Allows for control of a color changing device by setting its hue,
saturation, and color values

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-16
    name: Color Control
    status: live
    attributes:
      color:
        schema:
          type: object
          properties:
            value:
              $ref: String
        type: STRING
        setter: setColor
        actedOnBy:
          - setHue
          - setSaturation
      hue:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER
        setter: setHue
      saturation:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER
        setter: setSaturation
    commands:
      setColor:
        arguments:
        - name: color
          required: true
          type: COLOR_MAP
          schema:
            type: object
            properties:
              value:
                $ref: color-map
      setHue:
        arguments:
        - name: hue
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
      setSaturation:
        arguments:
        - name: saturation
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
    public: true
    id: colorControl
    ocfResourceType: oic.r.colour.chroma
    version: 1

Color Temperature
-----------------

Set the color temperature attribute of a color changing device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-16
    name: Color Temperature
    status: live
    attributes:
      colorTemperature:
        schema:
          type: object
          properties:
            value:
              type: integer
              minimum: 1
              maximum: 30000
            unit:
              type: string
              enum:
               - K
              default: K
          required:
            - value
        type: NUMBER
        setter: setColorTemperature
    commands:
      setColorTemperature:
        arguments:
        - name: temperature
          required: true
          schema:
            type: integer
            minimum: 1
            maximum: 30000
          type: NUMBER
    public: true
    id: colorTemperature
    ocfResourceType: x.com.st.color.temperature
    version: 1

Color
-----

Allows for control of a color changing device by setting its hue and
saturation.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-16
    id: color
    status: proposed
    public: true
    name: Color
    attributes:
      colorValue:
        schema:
          type: object
          properties:
            value:
              type: object
              properties:
                hue:
                  type: number
                  minimum: 0.0
                  maximum: 360.0
                saturation:
                  type: number
                  minimum: 0.0
                  maximum: 100.0
          required:
            - value
        setter: setColorValue
        type: JSON_OBJECT
    commands:
      setColorValue:
        arguments:
          - name: colorValue
            required: true
            schema:
              type: object
              properties:
                hue:
                  type: number
                  minimum: 0.0
                  maximum: 360.0
                saturation:
                  type: number
                  minimum: 0.0
                  maximum: 100.0
              required:
                - hue
                - saturation
            type: JSON_OBJECT
          - name: switchLevel
            schema:
              type: integer
              minimum: 0
              maximum: 100
            type: NUMBER
            required: false
    version: 1

Color Mode
----------

Describes if a device is in color or color temperature mode if it
supports both since state is mutually exclusive.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-16
    id: colorMode
    status: proposed
    public: true
    name: Color Mode
    attributes:
      colorMode:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - color
                - colorTemperature
                - other
        type: ENUM
        values:
          - color
          - colorTemperature
          - other
    commands: {}
    version: 1

Configuration
-------------

Allow configuration of devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Configuration
    status: live
    attributes: {
      }
    commands:
      configure:
        arguments: [
          ]
    public: true
    id: configuration
    version: 1

Consumable
----------

For devices with replaceable components

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Consumable
    status: proposed
    attributes:
      consumableStatus:
        schema:
          type: object
          properties:
            value:
              $ref: ConsumableState
          required:
            - value
        type: ENUM
        values:
          - good
          - maintenance_required
          - missing
          - order
          - replace
        setter: setConsumableStatus
    commands:
      setConsumableStatus:
        arguments:
        - name: status
          required: true
          schema:
            $ref: ConsumableState
          type: ENUM
          values:
            - good
            - maintenance_required
            - missing
            - order
            - replace
    public: true
    id: consumable
    version: 1

Contact Sensor
--------------

Allows reading the value of a contact sensor device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Contact Sensor
    status: live
    attributes:
      contact:
        schema:
          type: object
          properties:
            value:
              $ref: ContactState
          required: ["value"]
        type: ENUM
        values:
          - closed
          - open
    commands: {
      }
    public: true
    id: contactSensor
    ocfResourceType: oic.r.sensor.contact
    version: 1

Demand Response Load Control
----------------------------

Allows requests to be made to appliances to temporarily reduce their
energy usage to reduce demand on the power grid

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Demand Response Load Control
    status: proposed
    attributes:
      drlcStatus:
        schema:
          type: object
          properties:
            value:
              $ref: DemandResponseLoadControlStatus
          required: ["value"]
        type: JSON_OBJECT
    commands:
      requestDrlcAction:
        arguments:
        - name: drlcType
          required: true
          schema:
            $ref: DrlcType
          type: NUMBER
        - name: drlcLevel
          required: true
          schema:
            $ref: DrlcLevel
          type: NUMBER
        - name: start
          required: true
          schema:
            $ref: Iso8601Date
          type: STRING
        - name: duration
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
        - name: reportingPeriod
          schema:
            $ref: PositiveInteger
          type: NUMBER
          required: false
      overrideDrlcAction:
        arguments:
        - name: value
          required: true
          schema:
            type: boolean
          type: BOOLEAN

    public: true
    id: demandResponseLoadControl
    ocfResourceType: oic.r.energy.drlc   #https://oneiota.org/revisions/1761
    version: 1

Dishwasher Mode
---------------

Allows for the control of the dishwasher.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Dishwasher Mode
    status: proposed
    attributes:
      dishwasherMode:
        schema:
          type: object
          properties:
            value:
              $ref: DishwasherMode
          required: ["value"]
        type: ENUM
        values:
          - auto
          - quick
          - rinse
          - dry
        setter: setDishwasherMode
    commands:
      setDishwasherMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: DishwasherMode
          type: ENUM
          values:
            - auto
            - quick
            - rinse
            - dry
    public: true
    id: dishwasherMode
    ocfResourceType: x.com.st.mode.dishwasher
    version: 1

Dishwasher Operating State
--------------------------

Allows for the control of the dishwasher operational state.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Dishwasher Operating State
    status: proposed
    attributes:
      machineState:
        schema:
          type: object
          properties:
            value:
              $ref: MachineState
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: MachineState
          required: ["value"]
        type: ENUM
        values:
          - pause
          - run
          - stop
        setter: setMachineState
      supportedMachineStates:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: MachineState
          requires:
            - value
        type: JSON_OBJECT
      dishwasherJobState:
        schema:
          type: object
          properties:
            value:
              $ref: DishwasherJobState
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: DishwasherJobState
          required: ["value"]
        type: ENUM
        values:
          - airwash
          - cooling
          - drying
          - finish
          - preDrain
          - prewash
          - rinse
          - spin
          - unknown
          - wash
          - wrinklePrevent
      completionTime:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
    commands:
      setMachineState:
        arguments:
        - name: state
          required: true
          schema:
            $ref: MachineState
          type: ENUM
          values:
          - pause
          - run
          - stop
    public: true
    id: dishwasherOperatingState
    ocfResourceType: x.com.st.operationalstate.dishwasher
    version: 1

Door Control
------------

Allow for the control of a door

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Door Control
    status: live
    attributes:
      door:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - closed
                - closing
                - open
                - opening
                - unknown
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    type: string
                    enum:
                      - closed
                      - closing
                      - open
                      - opening
                      - unknown
          required:
            - value
        type: ENUM
        values:
          - closed
          - closing
          - open
          - opening
          - unknown
        enumCommands:
          - command: close
            value: closed
          - command: open
            value: open
    commands:
      close:
        arguments: [
          ]
      open:
        arguments: [
          ]
    public: true
    id: doorControl
    ocfResourceType: x.com.st.doorcontrol
    version: 1

Dryer Mode
----------

Allows for the control of the dryer.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Dryer Mode
    status: proposed
    attributes:
      dryerMode:
        schema:
          type: object
          properties:
            value:
              $ref: DryerMode
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: DryerMode
          required: ["value"]
        type: ENUM
        values:
          - regular
          - lowHeat
          - highHeat
        setter: setDryerMode
    commands:
      setDryerMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: DryerMode
          type: ENUM
          values:
            - regular
            - lowHeat
            - highHeat
    public: true
    id: dryerMode
    ocfResourceType: x.com.st.mode.dryer
    version: 1

Dryer Operating State
---------------------

Allows for the control of the dryer operational state.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Dryer Operating State
    status: proposed
    attributes:
      machineState:
        schema:
          type: object
          properties:
            value:
              $ref: MachineState
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: MachineState
          required: ["value"]
        type: ENUM
        values:
          - pause
          - run
          - stop
        setter: setMachineState
      supportedMachineStates:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: MachineState
          required: ["value"]
        type: JSON_OBJECT
      dryerJobState:
        schema:
          type: object
          properties:
            value:
              $ref: DryerJobState
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: DryerJobState
          required: ["value"]
        type: ENUM
        values:
          - cooling
          - delayWash
          - drying
          - finished
          - none
          - weightSensing
          - wrinklePrevent
      completionTime:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
    commands:
      setMachineState:
        arguments:
        - name: state
          required: true
          schema:
            $ref: MachineState
          type: ENUM
          values:
            - pause
            - run
            - stop
    public: true
    id: dryerOperatingState
    ocfResourceType: x.com.st.operationalstate.dryer
    version: 1

Dust Sensor
-----------

Gets the reading of the dust sensor.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Dust Sensor
    status: proposed
    attributes:
      fineDustLevel:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
      dustLevel:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
    commands: {
      }
    public: true
    id: dustSensor
    ocfResourceType: x.com.st.dustlevel
    version: 1

Energy Meter
------------

Read the energy consumption of an energy metering device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Energy Meter
    status: live
    attributes:
      energy:
        schema:
          type: object
          properties:
            value:
              type: number
            unit:
              type: string
              enum:
                - kWh
              default: kWh
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: energyMeter
    ocfResourceType: x.com.st.energymeter
    version: 1

Estimated Time Of Arrival
-------------------------

Allow access to estimated time of arrival values for devices that
support it, for example automobiles

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Estimated Time Of Arrival
    status: proposed
    attributes:
      eta:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
    commands: {
      }
    public: true
    id: estimatedTimeOfArrival
    version: 1

Execute
-------

Allows for raw messages to be passed to a device.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Execute
    status: proposed
    attributes:
      data:
        schema:
           type: object
           properties:
             value:
               $ref: JsonObject
           required:
             - value
        type: JSON_OBJECT
        actedOnBy:
          - 'execute'
    commands:
      'execute':
        arguments:
          - name: command
            required: true
            type: STRING
            schema:
              $ref: String
          - name: args
            schema:
              $ref: JsonObject
            type: JSON_OBJECT
            required: false
    public: true
    id: execute
    version: 1

Fan Speed
---------

Allows for the control of the fan speed.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Fan Speed
    status: proposed
    attributes:
      fanSpeed:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
        setter: setFanSpeed
    commands:
      setFanSpeed:
        arguments:
        - name: speed
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
    public: true
    id: fanSpeed
    ocfResourceType: x.com.st.fanspeed
    version: 1

Filter Status
-------------

Gets the status of the filter.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Filter Status
    status: proposed
    attributes:
      filterStatus:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - normal
                - replace
          required: ["value"]
        type: ENUM
        values:
          - normal
          - replace
    commands: {
      }
    public: true
    id: filterStatus
    ocfResourceType: x.com.st.filter
    version: 1

Garage Door Control
-------------------

Allow for the control of a garage door. Deprecated in favor of Door
Control.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Garage Door Control
    status: deprecated
    attributes:
      door:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - closed
                - closing
                - open
                - opening
                - unknown
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    type: string
                    enum:
                      - closed
                      - closing
                      - open
                      - opening
                      - unknown
          required:
            - value
        type: ENUM
        values:
          - closed
          - closing
          - open
          - opening
          - unknown
        enumCommands:
          - command: close
            value: closed
          - command: open
            value: open
    commands:
      close:
        arguments: [
          ]
      open:
        arguments: [
          ]
    public: true
    id: garageDoorControl
    ocfResourceType: x.com.st.garagedoorcontrol
    version: 1

Geolocation
-----------

Gets the value of the geo location.

Definition
^^^^^^^^^^

.. code-block:: yaml

    id: geolocation
    name: Geolocation
    status: proposed
    public: true
    attributes:
      latitude:
        schema:
          type: object
          properties:
            value:
              type: integer
              maximum: 90
              minimum: -90
        type: NUMBER
      longitude:
        schema:
          type: object
          properties:
            value:
              type: integer
              maximum: 180
              minimum: -180
        type: NUMBER
      method:
        schema:
          type: object
          properties:
            value:
              $ref: String
        type: STRING
      accuracy:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              # maximum: ??
        type: NUMBER
      altitudeAccuracy:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              # maximum: ??
        type: NUMBER
      heading:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              maximum: 360
        type: NUMBER
      speed:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              # maximum: ??
        type: NUMBER
      lastUpdateTime:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER

    commands: {
      }
    version: 1

Holdable Button
---------------

A device with one or more holdable buttons. Deprecated in favor of
Button.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Holdable Button
    status: deprecated
    attributes:
      button:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - held
                - pushed
          required:
            - value
        type: ENUM
        values:
          - held
          - pushed
      numberOfButtons:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: holdableButton
    version: 1

Illuminance Measurement
-----------------------

Gives the illuminance reading from devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Illuminance Measurement
    status: live
    attributes:
      illuminance:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              maximum: 100000
            unit:
              type: string
              enum:
                - lux
              default: lux
          required: ["value"]
        type: NUMBER
        unit: lux
    commands: {
      }
    public: true
    id: illuminanceMeasurement
    ocfResourceType: oic.r.sensor.illuminance
    version: 1

Image Capture
-------------

Allows for the capture of an image on devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Image Capture
    status: proposed
    attributes:
      image:
        schema:
          type: object
          properties:
            value:
              $ref: URL
          required:
            - value
        type: STRING
        setter: take
    commands:
      take:
        arguments: [
          ]
    public: true
    id: imageCapture
    ocfResourceType: x.com.st.imagecapture
    version: 1

Indicator
---------

The indicator capability gives you the ability to set the indicator LED
light on a Z-Wave switch. As such, the most common use case for the
indicator capability is in a Device Handler.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Indicator
    status: deprecated
    attributes:
      indicatorStatus:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - never
                - when off
                - when on
          required:
            - value
        type: ENUM
        values:
        - never
        - when off
        - when on
        enumCommands:
          - command: indicatorNever
            value: never
          - command: indicatorWhenOff
            value: when off
          - command: indicatorWhenOn
            value: when on
    commands:
      indicatorNever:
        arguments: [
          ]
      indicatorWhenOff:
        arguments: [
          ]
      indicatorWhenOn:
        arguments: [
          ]
    public: true
    id: indicator
    version: 1

Infrared Level
--------------

Allows for the control of the infrared level attribute of a device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Infrared Level
    status: live
    attributes:
      infraredLevel:
        schema:
          $ref: Percent
        type: NUMBER
        setter: setInfraredLevel
    commands:
      setInfraredLevel:
        arguments:
        - name: level
          required: true
          schema:
            type: number
            minimum: 0
            maximum: 100
          type: NUMBER
    public: true
    id: infraredLevel
    version: 1

Light
-----

Allows for the control of a light device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Light
    status: deprecated
    attributes:
      switch:
        schema:
          type: object
          properties:
            value:
              $ref: SwitchState
          required: ["value"]
        type: ENUM
        values:
          - 'off'
          - 'on'
        enumCommands:
          - command: 'on'
            value: 'on'
          - command: 'off'
            value: 'off'
    commands:
      'off':
        arguments: [
          ]
      'on':
        arguments: [
          ]
    public: true
    id: light
    version: 1

Lock Only
---------

Allow for the lock control of a lock device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Lock Only
    status: deprecated
    attributes:
      lock:
        schema:
          type: object
          properties:
            value:
              $ref: LockState
          required:
            - value
        type: ENUM
        values:
          - locked
          - unknown
          - unlocked
          - unlocked with timeout
        enumCommands:
          - command: lock
            value: locked
    commands:
      lock:
        arguments: [
          ]
    public: true
    id: lockOnly
    version: 1

Lock
----

Allow for the control of a lock device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Lock
    status: proposed
    attributes:
      lock:
        schema:
          type: object
          properties:
            value:
              $ref: LockState
            data:
              type: object
              properties:
                method:
                  type: string
                  enum:
                    - manual
                    - keypad
                    - auto
                    - command
                codeId:
                  type: string
                timeout:
                  $ref: Iso8601Date
          required:
            - value
        type: ENUM
        values:
          - locked
          - unknown
          - unlocked
          - unlocked with timeout
        enumCommands:
          - command: lock
            value: locked
          - command: unlock
            value: unlocked
    commands:
      lock:
        arguments: [
          ]
      unlock:
        arguments: [
          ]
    public: true
    id: lock
    ocfResourceType: oic.r.lock.status
    version: 1

Media Controller
----------------

Allows for the control of a media controller device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Media Controller
    status: proposed
    attributes:
      activities:
        schema:
           type: object
           properties:
             value:
               $ref: JsonObject
           required:
             - value
        type: JSON_OBJECT
        actedOnBy:
          - startActivity
      currentActivity:
        schema:
           type: object
           properties:
             value:
               $ref: String
           required:
             - value
        type: STRING
        actedOnBy:
          - startActivity
    commands:
      startActivity:
        arguments:
        - type: STRING
          required: true
          schema:
            $ref: String
          name: activityId
    public: true
    id: mediaController
    version: 1

Media Input Source
------------------

Allows for the control of the media input source.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Input Source
    status: proposed
    attributes:
      inputSource:
        schema:
          type: object
          properties:
            value:
              $ref: MediaSource
          required: ["value"]
        type: ENUM
        values:
          - AM
          - CD
          - FM
          - HDMI
          - HDMI2
          - USB
          - YouTube
          - aux
          - bluetooth
          - digital
          - melon
          - wifi
        setter: setInputSource
      supportedInputSources:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: MediaSource
          required: ["value"]

        type: JSON_OBJECT
    commands:
      setInputSource:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: MediaSource
          type: ENUM
          values:
            - AM
            - CD
            - FM
            - HDMI
            - HDMI2
            - USB
            - YouTube
            - aux
            - bluetooth
            - digital
            - melon
            - wifi
    public: true
    id: mediaInputSource
    ocfResourceType: x.com.st.mediainputsource
    version: 1

Media Playback Repeat
---------------------

Allows for the control of the media playback repeat.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Playback Repeat
    status: proposed
    attributes:
      playbackRepeatMode:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - all
                - 'off'
                - one
          required: ["value"]
        type: ENUM
        values:
          - all
          - 'off'
          - one
        setter: setPlaybackRepeatMode
    commands:
      setPlaybackRepeatMode:
        arguments:
        - name: mode
          required: true
          schema:
            type: string
            enum:
              - all
              - 'off'
              - one
          type: ENUM
          values:
            - all
            - 'off'
            - one
    public: true
    id: mediaPlaybackRepeat
    ocfResourceType: x.com.st.mediarepeat
    version: 1

Media Playback Shuffle
----------------------

Allows for the control of media playback shuffle.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Playback Shuffle
    status: proposed
    attributes:
      playbackShuffle:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - disabled
                - enabled
          required: ["value"]
        type: ENUM
        values:
          - disabled
          - enabled
        setter: setPlaybackShuffle
    commands:
      setPlaybackShuffle:
        arguments:
        - name: shuffle
          required: true
          schema:
            type: string
            enum:
              - disabled
              - enabled
          type: ENUM
          values:
            - disabled
            - enabled
    public: true
    id: mediaPlaybackShuffle
    ocfResourceType: x.com.st.mediashuffle
    version: 1

Media Playback
--------------

Allows for the control of the media playback.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Playback
    status: proposed
    attributes:
      level:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER
      playbackStatus:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - pause
                - play
                - stop
        type: ENUM
        values:
          - pause
          - play
          - stop
        setter: setPlaybackStatus
        enumCommands:
          - command: play
            value: play
          - command: pause
            value: pause
          - command: stop
            value: stop
    commands:
      setPlaybackStatus:
        arguments:
        - name: status
          required: true
          schema:
            type: string
            enum:
              - pause
              - play
              - stop
          type: ENUM
          values:
            - pause
            - play
            - stop
      play:
        arguments: [
          ]
      pause:
        arguments: [
          ]
      stop:
        arguments: [
          ]
    public: true
    id: mediaPlayback
    ocfResourceType: x.com.st.mediaplayer
    version: 1

Media Presets
-------------

Allows setting a preset from a know list of presets for the media player


Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Presets
    status: proposed
    attributes:
      presets:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: MediaPreset
        type: JSON_OBJECT
    commands:
      selectPreset:
        arguments:
        - name: presetId
          required: true
          schema:
            $ref: String
          type: STRING
      playPreset:
        arguments:
        - name: presetId
          required: true
          schema:
            $ref: String
          type: STRING
    public: true
    id: mediaPresets
    version: 1

Media Track Control
-------------------

Allows for the media track control.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Media Track Control
    status: proposed
    attributes: {
      }
    commands:
      nextTrack:
        arguments: [
          ]
      previousTrack:
        arguments: [
          ]
    public: true
    id: mediaTrackControl
    ocfResourceType: x.com.st.mediatrackcontrol
    version: 1

Momentary
---------

Allows for the control of a momentary switch device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Momentary
    status: live
    attributes: {
      }
    commands:
      push:
        arguments: [
          ]
    public: true
    id: momentary
    ocfResourceType: x.com.st.momentary
    version: 1

Motion Sensor
-------------

Allows for the ability to read motion sensor device states

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Motion Sensor
    status: live
    attributes:
      motion:
        schema:
          type: object
          properties:
            value:
              $ref: ActivityState
          required: ["value"]
        type: ENUM
        values:
          - active
          - inactive
    commands: {
      }
    public: true
    id: motionSensor
    ocfResourceType: oic.r.sensor.motion
    version: 1

Music Player
------------

Allows for control of a music playing device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-22
    name: Music Player
    status: deprecated
    attributes:
      level:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER
        setter: setLevel
      mute:
        schema:
          type: object
          properties:
            value:
              $ref: MuteState
        type: ENUM
        values:
          - muted
          - unmuted
        enumCommands:
          - command: mute
            value: muted
          - command: unmute
            value: unmuted
      status:
        schema:
          type: object
          properties:
            value:
              $ref: String
        type: STRING
        actedOnBy:
          - nextTrack
          - pause
          - play
          - playTrack
          - previousTrack
          - restoreTrack
          - resumeTrack
          - setTrack
          - stop
      trackData:
        schema:
          type: object
          properties:
            value:
              $ref: JsonObject
        type: JSON_OBJECT
        actedOnBy:
          - nextTrack
          - pause
          - play
          - playTrack
          - previousTrack
          - restoreTrack
          - resumeTrack
          - setTrack
          - stop
      trackDescription:
        schema:
          type: object
          properties:
            value:
              $ref: String
        type: STRING
    commands:
      mute:
        arguments: [
          ]
      nextTrack:
        arguments: [
          ]
      pause:
        arguments: [
          ]
      play:
        arguments: [
          ]
      playTrack:
        arguments:
        - name: trackToPlay
          required: true
          schema:
            $ref: String
          type: STRING
      previousTrack:
        arguments: [
          ]
      restoreTrack:
        arguments:
        - name: trackToRestore
          required: true
          schema:
            $ref: String
          type: STRING
      resumeTrack:
        arguments:
        - name: trackToResume
          required: true
          schema:
            $ref: String
          type: STRING
      setLevel:
        arguments:
        - name: level
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
      setTrack:
        arguments:
        - name: trackToSet
          required: true
          schema:
            $ref: String
          type: STRING
      stop:
        arguments: [
          ]
      unmute:
        arguments: [
          ]
    public: true
    id: musicPlayer
    version: 1

Notification
------------

Allows for displaying notifications on devices that allow notifications
to be displayed

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Notification
    status: live
    attributes: {
      }
    commands:
      deviceNotification:
        arguments:
        - name: notification
          required: true
          schema:
            $ref: String
          type: STRING
    public: true
    id: notification
    version: 1

Odor Sensor
-----------

Gets the odor sensor reading.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Odor Sensor
    status: proposed
    attributes:
      odorLevel:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
    commands: {
      }
    public: true
    id: odorSensor
    ocfResourceType: x.com.st.gaslevel
    version: 1

Outlet
------

Allows for the control of an outlet device. Deprecated in favor of
Switch.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Outlet
    status: deprecated
    attributes:
      switch:
        schema:
          type: object
          properties:
            value:
              $ref: SwitchState
          required:
            - value
        type: ENUM
        values:
          - 'off'
          - 'on'
        enumCommands:
          - command: 'on'
            value: 'on'
          - command: 'off'
            value: 'off'
    commands:
      'off':
        arguments: [
          ]
      'on':
        arguments: [
          ]
    public: true
    id: outlet
    version: 1

Oven Mode
---------

Allows for the control of the oven mode.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Oven Mode
    status: proposed
    attributes:
      ovenMode:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - heating
                - grill
                - warming
                - defrosting
            constraints:
              constraints:
                type: object
                properties:
                  values:
                    type: array
                    items:
                      type: string
                      enum:
                        - heating
                        - grill
                        - warming
                        - defrosting
          required: ["value"]
        type: ENUM
        values:
          - heating
          - grill
          - warming
          - defrosting
        setter: setOvenMode
    commands:
      setOvenMode:
        arguments:
        - name: mode
          required: true
          schema:
            type: string
            enum:
              - heating
              - grill
              - warming
              - defrosting
          type: ENUM
          values:
            - heating
            - grill
            - warming
            - defrosting
    public: true
    id: ovenMode
    ocfResourceType: x.com.st.mode.oven
    version: 1

Oven Operating State
--------------------

Allows for the control of the oven operational state.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Oven Operating State
    status: proposed
    attributes:
      machineState:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - ready
                - running
                - paused
        type: ENUM
        values:
          - ready
          - running
          - paused
        setter: setMachineState
        actedOnBy:
          - stop
      supportedMachineStates:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                type: string
                enum:
                  - ready
                  - running
                  - paused
        type: JSON_OBJECT
      ovenJobState:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - cleaning
                - cooking
                - cooling
                - draining
                - preheat
                - ready
                - rinsing
        type: ENUM
        values:
          - cleaning
          - cooking
          - cooling
          - draining
          - preheat
          - ready
          - rinsing
      completionTime:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
      operationTime:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
        type: NUMBER
        actedOnBy:
          - stop
      progress:
        schema:
          $ref: IntegerPercent
        type: NUMBER
    commands:
      setMachineState:
        arguments:
        - name: state
          required: true
          schema:
            type: string
            enum:
              - stop
          type: ENUM
          values:
            - stop
      stop:
        arguments: [
          ]
    public: true
    id: ovenOperatingState
    ocfResourceType: x.com.st.operationalstate.oven
    version: 1

Oven Setpoint
-------------

Allows for the control of the oven set point.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Oven Setpoint
    status: proposed
    attributes:
      ovenSetpoint:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
        setter: setOvenSetpoint
    commands:
      setOvenSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: PositiveInteger
          type: NUMBER
    public: true
    id: ovenSetpoint
    ocfResourceType: x.com.st.temperature.oven
    version: 1

pH Measurement
--------------

Read the pH value off of a pH measurement capable device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: pH Measurement
    status: live
    attributes:
      pH:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              maximum: 14
            unit:
              type: string
              enum:
                - pH
              default: pH
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: pHMeasurement
    version: 1

Polling
-------

Allows for the polling of devices that support it. Deprecated, devices
should schedule their own polling using the scheduling API or use the
Ping capability.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-20
    name: Polling
    status: deprecated
    attributes: {
      }
    commands:
      poll:
        arguments: [
          ]
    public: true
    id: polling
    version: 1

Power Consumption Report
------------------------

Allows periodically reporting the energy and power consumption

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Power Consumption Report
    status: proposed
    attributes:
      powerConsumption:
        schema:
          type: object
          properties:
            value:
              $ref: PowerConsumption
          required: ["value"]
        type: JSON_OBJECT
    commands: {
    }

    public: true
    id: powerConsumptionReport
    version: 1

Power Meter
-----------

Allows for reading the power consumption from devices that report it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-20
    name: Power Meter
    status: live
    attributes:
      power:
        schema:
          type: object
          properties:
            value:
              type: number
            unit:
              type: string
              enum:
                - W
              default: W
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: powerMeter
    ocfResourceType: x.com.st.powermeter
    version: 1

Power Source
------------

Gives the ability to determine the current power source of the device


Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Power Source
    status: live
    attributes:
      powerSource:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - battery
                - dc
                - mains
                - unknown
          required: ["value"]
        type: ENUM
        values:
          - battery
          - dc
          - mains
          - unknown
    commands: {
      }
    public: true
    id: powerSource
    version: 1

Presence Sensor
---------------

The ability to see the current status of a presence sensor device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Presence Sensor
    status: live
    attributes:
      presence:
        schema:
          type: object
          properties:
            value:
              $ref: PresenceState
          required: ["value"]
        type: ENUM
        values:
          - not present
          - present
    commands: {
      }
    public: true
    id: presenceSensor
    ocfResourceType: oic.r.sensor.presence
    version: 1

Rapid Cooling
-------------

Allows for the control of rapid cooling.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Rapid Cooling
    status: proposed
    attributes:
      rapidCooling:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - 'off'
                - 'on'
          required: ["value"]
        type: ENUM
        values:
          - 'off'
          - 'on'
        setter: setRapidCooling
    commands:
      setRapidCooling:
        arguments:
        - name: rapidCooling
          required: true
          schema:
            type: string
            enum:
              - 'off'
              - 'on'
          type: ENUM
          values:
            - 'off'
            - 'on'
    public: true
    id: rapidCooling
    ocfResourceType: x.com.st.rapidcooling
    version: 1

Refresh
-------

Allow the execution of the refresh command for devices that support it


Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Refresh
    status: live
    attributes: {
      }
    commands:
      refresh:
        arguments: [
          ]
    public: true
    id: refresh
    version: 1

Refrigeration Setpoint
----------------------

Allows for the control of the refrigeration set point.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Refrigeration Setpoint
    status: proposed
    attributes:
      refrigerationSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
        setter: setRefrigerationSetpoint
    commands:
      setRefrigerationSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: TemperatureValue
          type: NUMBER
    public: true
    id: refrigerationSetpoint
    ocfResourceType: x.com.st.temperature.refrigeration
    version: 1

Relative Humidity Measurement
-----------------------------

Allow reading the relative humidity from devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Relative Humidity Measurement
    status: live
    attributes:
      humidity:
        schema:
          $ref: Percent
        type: NUMBER
    commands: {
      }
    public: true
    id: relativeHumidityMeasurement
    ocfResourceType: oic.r.humidity
    version: 1

Relay Switch
------------

Allows for the control of a relay switch device. This is **deprecated**
please use switch instead.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Relay Switch
    status: deprecated
    attributes:
      switch:
        schema:
          type: object
          properties:
            value:
              $ref: SwitchState
          required: ["value"]
        type: ENUM
        values:
          - 'off'
          - 'on'
        enumCommands:
          - command: 'on'
            value: 'on'
          - command: 'off'
            value: 'off'
    commands:
      'off':
        arguments: [
          ]
      'on':
        arguments: [
          ]
    public: true
    id: relaySwitch
    version: 1

Robot Cleaner Cleaning Mode
---------------------------

Allows for the control of the robot cleaner cleaning mode.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Robot Cleaner Cleaning Mode
    status: proposed
    attributes:
      robotCleanerCleaningMode:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - auto
                - part
                - repeat
                - manual
                - stop
                - map
          required: ["value"]
        type: ENUM
        values:
          - auto
          - part
          - repeat
          - manual
          - stop
          - map
        setter: setRobotCleanerCleaningMode
    commands:
      setRobotCleanerCleaningMode:
        arguments:
        - name: mode
          required: true
          schema:
            type: string
            enum:
              - auto
              - part
              - repeat
              - manual
              - stop
          type: ENUM
          values:
            - auto
            - part
            - repeat
            - manual
            - stop
    public: true
    id: robotCleanerCleaningMode
    ocfResourceType: x.com.st.robot.cleaner.cleaning
    version: 1

Robot Cleaner Movement
----------------------

Allows for the control of the robot cleaner movement.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Robot Cleaner Movement
    status: proposed
    attributes:
      robotCleanerMovement:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - homing
                - idle
                - charging
                - alarm
                - powerOff
                - reserve
                - point
                - after
                - cleaning
          required: ["value"]
        type: ENUM
        values:
          - homing
          - idle
          - charging
          - alarm
          - powerOff
          - reserve
          - point
          - after
          - cleaning
        setter: setRobotCleanerMovement
    commands:
      setRobotCleanerMovement:
        arguments:
        - name: mode
          required: true
          schema:
            type: string
            enum:
              - homing
          type: ENUM
          values:
            - homing
    public: true
    id: robotCleanerMovement
    ocfResourceType: x.com.st.robot.cleaner.movement
    version: 1

Robot Cleaner Turbo Mode
------------------------

Allows for the control of the robot cleaner turbo mode.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Robot Cleaner Turbo Mode
    status: proposed
    attributes:
      robotCleanerTurboMode:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - 'on'
                - 'off'
                - 'silence'
          required: ["value"]
        type: ENUM
        values:
          - 'on'
          - 'off'
          - 'silence'
        setter: setRobotCleanerTurboMode
    commands:
      setRobotCleanerTurboMode:
        arguments:
        - name: mode
          required: true
          schema:
            type: string
            enum:
              - 'on'
              - 'off'
              - 'silence'
          type: ENUM
          values:
            - 'on'
            - 'off'
            - 'silence'
    public: true
    id: robotCleanerTurboMode
    ocfResourceType: x.com.st.robot.cleaner.turbo
    version: 1

Sensor
------

The Sensor capability is a "tagging" capability. It defines no
attributes or commands. In SmartThings terms, it represents that a
Device has attributes.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Sensor
    status: deprecated
    attributes: {
      }
    commands: {
      }
    public: true
    id: sensor
    version: 1

Shock Sensor
------------

A Device that senses whether or not there is a shock

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Shock Sensor
    status: deprecated
    attributes:
      shock:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - clear
                - detected
          required: ["value"]
        type: ENUM
        values:
          - clear
          - detected
    commands: {
      }
    public: true
    id: shockSensor
    version: 1

Signal Strength
---------------

Gives the ability to read the signal stregth of Devices that support it


Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Signal Strength
    status: live
    attributes:
      lqi:
        schema:
          type: object
          properties:
            value:
              type: integer
              minimum: 0
              maximum: 255
          required:
            - value
        type: NUMBER
      rssi:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: -200
              maximum: 0
            unit:
              type: string
              enum:
                - dBm
              default: dBm
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: signalStrength
    ocfResourceType: x.com.st.signalstrength
    version: 1

Sleep Sensor
------------

A Device that senses whether or not someone is sleeping

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Sleep Sensor
    status: live
    attributes:
      sleeping:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - not sleeping # if ever replaced deal with this space (awake)
                - sleeping
          required: ["value"]
        type: ENUM
        values:
          - not sleeping
          - sleeping
    commands: {
      }
    public: true
    id: sleepSensor
    version: 1

Smoke Detector
--------------

A device that detects the presence or absence of smoke.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Smoke Detector
    status: live
    attributes:
      smoke:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - clear
                - detected
                - tested
          required: ["value"]
        type: ENUM
        values:
          - clear
          - detected
          - tested
    commands: {
      }
    public: true
    id: smokeDetector
    ocfResourceType: x.com.st.smokedetector
    version: 1

Sound Pressure Level
--------------------

Gets the value of the sound pressure level.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Sound Pressure Level
    status: proposed
    attributes:
      soundPressureLevel:
        schema:
          type: object
          properties:
            value:
              type: number
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: soundPressureLevel
    version: 1

Sound Sensor
------------

A Device that senses sound

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Sound Sensor
    status: live
    attributes:
      sound:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - detected
                - not detected
          required: ["value"]
        type: ENUM
        values:
          - detected
          - not detected
    commands: {
      }
    public: true
    id: soundSensor
    ocfResourceType: x.com.st.soundsensor
    version: 1

Speech Recognition
------------------

Gets the spoken phrase string.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Speech Recognition
    status: proposed
    attributes:
      phraseSpoken:
        schema:
          type: object
          properties:
            value:
              type: string
              maxLength: 1000
          required:
            - value
        type: STRING
    commands: {
      }
    public: true
    id: speechRecognition
    version: 1

Speech Synthesis
----------------

Allows for the control by speech.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Speech Synthesis
    status: proposed
    attributes: {
      }
    commands:
      speak:
        arguments:
        - name: phrase
          required: true
          schema:
            type: string
            maxLength: 1000
          type: STRING
    public: true
    id: speechSynthesis
    version: 1

Step Sensor
-----------

A Device that works as a step counter

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Step Sensor
    status: proposed
    attributes:
      goal:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
      steps:
        schema:
          type: object
          properties:
            value:
              $ref: PositiveInteger
          required: ["value"]
        type: NUMBER
    commands: {
      }
    public: true
    id: stepSensor
    version: 1

Switch Level
------------

Allows for the control of the level attribute of a light

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09 pending decision on rate
    name: Switch Level
    status: live
    attributes:
      level:
        schema:
          $ref: IntegerPercent
        type: NUMBER
        setter: setLevel
    commands:
      setLevel:
        arguments:
        - name: level
          schema:
            type: integer
            minimum: 0
            maximum: 100
          type: NUMBER
          required: true
        - name: rate
          schema:
            $ref: PositiveInteger
          type: NUMBER
          required: false
    public: true
    id: switchLevel
    ocfResourceType: oic.r.light.dimming
    version: 1

Switch
------

Allows for the control of a switch device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Switch
    status: live
    attributes:
      switch:
        schema:
          type: object
          properties:
            value:
              $ref: SwitchState
          required: ["value"]
        type: ENUM
        values:
          - 'off'
          - 'on'
        enumCommands:
          - command: 'on'
            value: 'on'
          - command: 'off'
            value: 'off'
    commands:
      'off':
        arguments: [
          ]
      'on':
        arguments: [
          ]
    public: true
    id: switch
    ocfResourceType: x.com.st.powerswitch
    version: 1

Tamper Alert
------------

Gets the value of the tamper alert.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Tamper Alert
    status: live
    attributes:
      tamper:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - clear
                - detected
          required:
             - value
        type: ENUM
        values:
          - clear
          - detected
    commands: {
      }
    public: true
    id: tamperAlert
    ocfResourceType: x.com.st.tamperalert
    version: 1

Temperature Measurement
-----------------------

Get the temperature from a Device that reports current temperature

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Temperature Measurement
    status: live
    attributes:
      temperature:
        schema:
          type: object
          properties:
            value:
              $ref: TemperatureValue
            unit:
              $ref: TemperatureUnit
          required: ["value", "unit"]
        type: NUMBER
    commands: {
      }
    public: true
    id: temperatureMeasurement
    ocfResourceType: x.com.st.temperature.measured
    version: 1

Thermostat Cooling Setpoint
---------------------------

Allows for setting the cooling setpoint on a thermostat

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Cooling Setpoint
    status: live
    attributes:
      coolingSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
        setter: setCoolingSetpoint
    commands:
      setCoolingSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: TemperatureValue
          type: NUMBER
    public: true
    id: thermostatCoolingSetpoint
    ocfResourceType: x.com.st.temperature.cooling
    version: 1

Thermostat Fan Mode
-------------------

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Fan Mode
    status: live
    attributes:
      thermostatFanMode:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatFanMode
          required:
            - value
        type: ENUM
        values:
          - auto
          - circulate
          - followschedule
          - 'on'
        setter: setThermostatFanMode
        enumCommands:
          - command: fanAuto
            value: auto
          - command: fanCirculate
            value: circulate
          - command: fanOn
            value: 'on'
      supportedThermostatFanModes:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: ThermostatFanMode

        type: JSON_OBJECT
    commands:
      fanAuto:
        arguments: [
          ]
      fanCirculate:
        arguments: [
          ]
      fanOn:
        arguments: [
          ]
      setThermostatFanMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: ThermostatFanMode
          type: ENUM
          values:
            - auto
            - circulate
            - followschedule
            - 'on'
    public: true
    id: thermostatFanMode
    ocfResourceType: x.com.st.mode.fan.thermostat
    version: 1

Thermostat Heating Setpoint
---------------------------

Allows for setting the heating setpoint on a thermostat

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Heating Setpoint
    status: live
    attributes:
      heatingSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
        setter: setHeatingSetpoint
    commands:
      setHeatingSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: TemperatureValue
          type: NUMBER
    public: true
    id: thermostatHeatingSetpoint
    ocfResourceType: x.com.st.temperature.heating
    version: 1

Thermostat Mode
---------------

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Mode
    status: live
    attributes:
      thermostatMode:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatMode
          required:
            - value
        type: ENUM
        values:
          - auto
          - eco
          - rush hour
          - cool
          - emergency heat
          - heat
          - 'off'
        setter: setThermostatMode
        enumCommands:
          - command: auto
            value: auto
          - command: cool
            value: cool
          - command: emergencyHeat
            value: emergency heat
          - command: heat
            value: heat
          - command: 'off'
            value: 'off'
      supportedThermostatModes:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: ThermostatMode
        type: JSON_OBJECT
    commands:
      auto:
        arguments: [
          ]
      cool:
        arguments: [
          ]
      emergencyHeat:
        arguments: [
          ]
      heat:
        arguments: [
          ]
      'off':
        arguments: [
          ]
      setThermostatMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: ThermostatMode
          type: ENUM
          values:
            - auto
            - eco
            - rush hour
            - cool
            - emergency heat
            - heat
            - 'off'
    public: true
    id: thermostatMode
    ocfResourceType: x.com.st.mode.thermostat
    version: 1

Thermostat Operating State
--------------------------

Gives the ability to see the current state that the thermostat is
operating in

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Operating State
    status: live
    attributes:
      thermostatOperatingState:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatOperatingState
          required:
            - value
        type: ENUM
        values:
          - cooling
          - fan only
          - heating
          - idle
          - pending cool
          - pending heat
          - vent economizer
    commands: {
      }
    public: true
    id: thermostatOperatingState
    ocfResourceType: x.com.st.operationalstate.thermostat
    version: 1

Thermostat Setpoint
-------------------

Gives the ability to read the current setpoint on a thermostat

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat Setpoint
    status: deprecated
    attributes:
      thermostatSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
    commands: {
      }
    public: true
    id: thermostatSetpoint
    ocfResourceType: x.com.st.temperature.setpoint
    version: 1

Thermostat
----------

Allows for the control of a thermostat device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-30
    name: Thermostat
    status: deprecated
    attributes:
      coolingSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
        setter: setCoolingSetpoint
      coolingSetpointRange:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                -  $ref: TemperatureValue
                -  $ref: TemperatureValue
              minItems: 2
              maxItems: 2
          required:
            - value
        type: VECTOR3
      heatingSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
        setter: setHeatingSetpoint
      heatingSetpointRange:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                -  $ref: TemperatureValue
                -  $ref: TemperatureValue
              minItems: 2
              maxItems: 2
          required:
            - value
        type: VECTOR3
      schedule:
        schema:
           type: object
           properties:
             value:
               $ref: JsonObject
           required:
             - value
        type: JSON_OBJECT
        setter: setSchedule
      temperature:
        schema:
          $ref: Temperature
        type: NUMBER
      thermostatFanMode:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatFanMode
          required:
            - value
        type: ENUM
        values:
          - auto
          - circulate
          - followschedule
          - 'on'
        setter: setThermostatFanMode
        enumCommands:
          - command: fanAuto
            value: auto
          - command: fanCirculate
            value: circulate
          - command: fanOn
            value: 'on'
      supportedThermostatFanModes:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: ThermostatFanMode
        type: JSON_OBJECT
      thermostatMode:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatMode
          required:
            - value
        type: ENUM
        values:
          - auto
          - eco
          - rush hour
          - cool
          - emergency heat
          - heat
          - 'off'
        setter: setThermostatMode
        enumCommands:
          - command: auto
            value: auto
          - command: cool
            value: cool
          - command: emergencyHeat
            value: emergency heat
          - command: heat
            value: heat
          - command: 'off'
            value: 'off'
      supportedThermostatModes:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: ThermostatMode
        type: JSON_OBJECT
      thermostatOperatingState:
        schema:
          type: object
          properties:
            value:
              $ref: ThermostatOperatingState
          required:
            - value
        type: ENUM
        values:
          - cooling
          - fan only
          - heating
          - idle
          - pending cool
          - pending heat
          - vent economizer
      thermostatSetpoint:
        schema:
          $ref: Temperature
        type: NUMBER
      thermostatSetpointRange:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                -  $ref: TemperatureValue
                -  $ref: TemperatureValue
              minItems: 2
              maxItems: 2
        type: VECTOR3
    commands:
      auto:
        arguments: [
          ]
      cool:
        arguments: [
          ]
      emergencyHeat:
        arguments: [
          ]
      fanAuto:
        arguments: [
          ]
      fanCirculate:
        arguments: [
          ]
      fanOn:
        arguments: [
          ]
      heat:
        arguments: [
          ]
      'off':
        arguments: [
          ]
      setCoolingSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: TemperatureValue
          type: NUMBER
      setHeatingSetpoint:
        arguments:
        - name: setpoint
          required: true
          schema:
            $ref: TemperatureValue
          type: NUMBER
      setSchedule:
        arguments:
        - name: schedule
          required: true
          schema:
            $ref: JsonObject
          type: JSON_OBJECT
      setThermostatFanMode:
        arguments:
        - name: fanmode
          required: true
          schema:
            $ref: ThermostatFanMode
          type: ENUM
          values:
            - auto
            - circulate
            - followschedule
            - 'on'
      setThermostatMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: ThermostatMode
          type: ENUM
          values:
            - auto
            - eco
            - rush hour
            - cool
            - emergency heat
            - heat
            - 'off'
    public: true
    id: thermostat
    version: 1

Three Axis
----------

Gives the three axis coordinates for devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Three Axis
    status: live
    attributes:
      threeAxis:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                type: integer
                minimum: -10000
                maximum: 10000
              minItems: 3
              maxItems: 3
            unit:
              type: string
              enum:
                - mG
              default: mG
          required:
            - value
        type: VECTOR3
    commands: {
      }
    public: true
    id: threeAxis
    version: 1

Timed Session
-------------

Allows for the control of the timed session.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-2-13
    name: Timed Session
    status: proposed
    attributes:
      sessionStatus:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - canceled
                - paused
                - running
                - stopped
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    type: string
                    enum:
                      - canceled
                      - paused
                      - running
                      - stopped
          required:
            - value
        type: ENUM
        values:
          - canceled
          - paused
          - running
          - stopped
        enumCommands:
          - command: cancel
            value: canceled
          - command: pause
            value: paused
          - command: start
            value: running
          - command: stop
            value: stopped
      completionTime:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
        setter: setCompletionTime
    commands:
      cancel:
        arguments: [
          ]
      pause:
        arguments: [
          ]
      setCompletionTime:
        arguments:
        - name: completionTime
          required: true
          schema:
            $ref: Iso8601Date
          type: DATE
      start:
        arguments: [
          ]
      stop:
        arguments: [
          ]
    public: true
    id: timedSession
    version: 1

Tone
----

Allows for the control of a device that can make an audible tone

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-15
    name: Tone
    status: live
    attributes: {
      }
    commands:
      beep:
        arguments: [
          ]
    public: true
    id: tone
    ocfResourceType: x.com.st.tone
    version: 1

Touch Sensor
------------

Gives the ability to get the touched status for devices that are touch
sensitive. This has been **deprecated** in favor of the button
capability

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-11
    name: Touch Sensor
    status: deprecated
    attributes:
      touch:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - touched
        type: ENUM
        values:
          - touched
    commands: {
      }
    public: true
    id: touchSensor
    version: 1

Tv Channel
----------

Allows for the control of the TV channel.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Tv Channel
    status: proposed
    attributes:
      tvChannel:
        schema:
          type: object
          properties:
            value:
              $ref: String
        type: STRING
        setter: setTvChannel
        actedOnBy:
          - channelDown
          - channelUp
    commands:
      setTvChannel:
        arguments:
        - name: channel
          required: true
          schema:
            $ref: String
          type: STRING
      channelUp:
        arguments: [
          ]
      channelDown:
        arguments: [
          ]
    public: true
    id: tvChannel
    ocfResourceType: x.com.st.tvchannel
    version: 1

Ultraviolet Index
-----------------

Gives the ability to get the ultraviolet index from devices that report
it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-15
    name: Ultraviolet Index
    status: live
    attributes:
      ultravioletIndex:
        schema:
          type: object
          properties:
            value:
              type: number
              minimum: 0
              maximum: 255
          required: ["value"]
        type: NUMBER
    commands: {
      }
    public: true
    id: ultravioletIndex
    version: 1

Valve
-----

Allows for the control of a valve device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-15
    name: Valve
    status: live
    attributes:
      valve:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - closed
                - open
          required: ["value"]
        type: ENUM
        values:
          - closed
          - open
        enumCommands:
          - command: close
            value: closed
          - command: open
            value: open
    commands:
      close:
        arguments: [
          ]
      open:
        arguments: [
          ]
    public: true
    id: valve
    ocfResourceType: x.com.st.valve
    version: 1

Video Clips
-----------

Video clip capture

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Video Clips
    status: proposed
    attributes:
      videoClip:
        schema:
           type: object
           properties:
             value:
               $ref: VideoClip
           required:
             - value
        type: JSON_OBJECT
        actedOnBy:
          - captureClip
    commands:
      captureClip:
        arguments:
          - name: duration
            required: true
            schema:
              $ref: PositiveInteger
            type: NUMBER
          - name: preFetch
            required: true
            schema:
              $ref: PositiveInteger
            type: NUMBER
    public: true
    id: videoClips
    version: 1

Video Stream
------------

Allows for the control of the video stream.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Video Stream
    status: proposed
    attributes:
      stream:
        schema:
          type: object
          properties:
            value:
              $ref: JsonObject
          required:
            - value
        type: JSON_OBJECT
        actedOnBy:
          - startStream
          - stopStream
    commands:
      startStream:
        arguments: [
          ]
      stopStream:
        arguments: [
          ]
    public: true
    id: videoStream
    ocfResourceType: x.com.st.videostream
    version: 1

Voltage Measurement
-------------------

Get the value of voltage measured from devices that support it

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-15
    name: Voltage Measurement
    status: live
    attributes:
      voltage:
        schema:
          type: object
          properties:
            value:
              $ref: Number
            unit:
              type: string
              enum:
                - V
              default: V
          required:
            - value
        type: NUMBER
    commands: {
      }
    public: true
    id: voltageMeasurement
    version: 1

Washer Mode
-----------

Allows for the control of the washer mode.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Washer Mode
    status: proposed
    attributes:
      washerMode:
        schema:
          type: object
          properties:
            value:
              $ref: WasherMode
          required: ["value"]
        type: ENUM
        values:
          - regular
          - heavy
          - rinse
          - spinDry
        setter: setWasherMode
    commands:
      setWasherMode:
        arguments:
        - name: mode
          required: true
          schema:
            $ref: WasherMode
          type: ENUM
          values:
            - regular
            - heavy
            - rinse
            - spinDry
    public: true
    id: washerMode
    ocfResourceType: x.com.st.mode.washer
    version: 1

Washer Operating State
----------------------

Allows for the control of the washer operational state.

Definition
^^^^^^^^^^

.. code-block:: yaml

    name: Washer Operating State
    status: proposed
    attributes:
      machineState:
        schema:
          type: object
          properties:
            value:
              $ref: MachineState
          required: ["value"]
        type: ENUM
        values:
          - pause
          - run
          - stop
        setter: setMachineState
      supportedMachineStates:
        schema:
          type: object
          properties:
            value:
              type: array
              items:
                $ref: MachineState
        type: JSON_OBJECT
      washerJobState:
        schema:
          type: object
          properties:
            value:
              type: string
              enum:
                - airWash
                - cooling
                - delayWash
                - drying
                - finish
                - none
                - preWash
                - rinse
                - spin
                - wash
                - weightSensing
                - wrinklePrevent
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    type: string
                    enum:
                      - airWash
                      - cooling
                      - delayWash
                      - drying
                      - finish
                      - none
                      - preWash
                      - rinse
                      - spin
                      - wash
                      - weightSensing
                      - wrinklePrevent
          required: ["value"]
        type: ENUM
        values:
          - airWash
          - cooling
          - delayWash
          - drying
          - finish
          - none
          - preWash
          - rinse
          - spin
          - wash
          - weightSensing
          - wrinklePrevent
      completionTime:
        schema:
          type: object
          properties:
            value:
              $ref: Iso8601Date
          required:
            - value
        type: DATE
    commands:
      setMachineState:
        arguments:
        - name: state
          required: true
          schema:
            $ref: MachineState
          type: ENUM
          values:
            - pause
            - run
            - stop
    public: true
    id: washerOperatingState
    ocfResourceType: x.com.st.operationalstate.washer
    version: 1

Water Sensor
------------

Get the status off of a water sensor device

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-01-09
    name: Water Sensor
    status: live
    attributes:
      water:
        schema:
          type: object
          properties:
            value:
              $ref: MoistureState
          required: ["value"]
        type: ENUM
        values:
          - dry
          - wet
    commands: {
      }
    public: true
    id: waterSensor
    ocfResourceType: oic.r.sensor.water
    version: 1

Window Shade
------------

Allows for the control of the window shade.

Definition
^^^^^^^^^^

.. code-block:: yaml

    # reviewed 2018-02-15
    name: Window Shade
    status: proposed
    attributes:
      windowShade:
        schema:
          type: object
          properties:
            value:
              $ref: OpenableState
            constraints:
              type: object
              properties:
                values:
                  type: array
                  items:
                    $ref: OpenableState
          required:
            - value
        type: ENUM
        values:
          - closed
          - closing
          - open
          - opening
          - partially open
          - unknown
        enumCommands:
          - command: close
            value: closed
          - command: open
            value: open
        actedOnBy:
          - presetPosition
    commands:
      close:
        arguments: [
          ]
      open:
        arguments: [
          ]
      presetPosition:
        arguments: [
          ]
    public: true
    id: windowShade
    version: 1
