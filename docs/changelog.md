# Changelog

## Version 0.5
- Support for enum values syntax with strings:
```groovy
input(... type: "enum", options: ["0": "Val1", "1": "Val2"], defaultValue: "1" ...)
```
- Bugfixes for capabilities.

## Version 0.4
Added partial validation of device's `parse()` method.