# Quality Attribute Scenario

## ID Generation

| Element          | Statement                                                                                                                                                                                      |
|------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Stimulus         | The persistence layer cannot generate object IDs                                                                                                                                               |
| Stimulus Source  | The system has been configured to use a persistence system that cannot automatically generate IDs                                                                                              |
| Environment      | The persistence method used by the system can change according to the client's preferences and is not guaranteed to handle ID generation or be able to use pre-existing IDs upon being changed |
| Artifact         | Library Management System                                                                                                                                                                      |
| Response         | Manually generate in-system IDs to be persisted alongside objects                                                                                                                              |
| Response Measure |                                                                                                                                                                                                |