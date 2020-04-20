##MSA Controller Simulator Project.

The simulator is a simple spring boot application which acts as the MSA controller for the MDONS use case

##The API'S are as follows:

1.Create Service : /cxf/openroadm/openroadm-services/create
2.Delete Service : /cxf/openroadm/openroadm-services/delete
3.Get Topology :  /cxf/openroadm/v2/networks/otn-topology

##
Sample create request:

{
   "common-id":"123456",
   "connection-type":"service",
   "service-name":"svc-Customer1-orange-001_MDONS_OTN_MSA",
   "sdnc-request-header":{
      "request-id":"fd532fb8-fb9a-477b-b0a9-f8f33ba79f23",
      "rpc-action":"service-create",
      "notification-url":"http://167.254.204.118:8090/receiver/services",
      "request-system-id":"SDN-MLC"
   },
   "service-a-end":{
      "service-rate":10,
      "service-format":"Ethernet",
      "clli":"OWB-AEND",
      "node-id":"OWBSPDRNE15",
      "optic-type":"gray",
      "ethernet-encoding":"10GBASE-R",
      "mapping-mode":"GFP-F",
      "tx-direction":{
         "port":{
            "port-device-name":"router-1",
            "port-name":"R1"
         }
      },
      "rx-direction":{
         "port":{
            "port-device-name":"router-1",
            "port-name":"R1"
         }
      }
   },
   "service-z-end":{
      "service-rate":10,
      "service-format":"Ethernet",
      "node-id":"OWBSPDRNE16",
      "clli":"OWB-ZEND",
      "optic-type":"gray",
      "ethernet-encoding":"10GBASE-R",
      "mapping-mode":"GFP-F",
      "tx-direction":{
         "port":{
            "port-device-name":"router-2",
            "port-name":"R2"
         }
      },
      "rx-direction":{
         "port":{
            "port-device-name":"router-2",
            "port-name":"R2"
         }
      }
   },
   "service-layer":"otn"
}

##Sample delete request


{
   "sdnc-request-header":{
      "request-id":"fd532fb8-fb9a-477b-b0a9-f8f33ba79f23",
      "rpc-action":"service-create",
      "notification-url":"http://167.254.204.118:8090/receiver/services",
      "request-system-id":"SDN-MLC"
   },
  "service-delete-req-info":{
    "tail-retention": "no",
    "service-name": "no"
  }
}

##To bring up locally along with the other instances go to the main directory dc-simluator
Execute docker-compose up


##To compile and build 
mvn clean install


##To build docker image 
mvn package -P docker




