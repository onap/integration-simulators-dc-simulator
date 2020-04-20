TAPI Controller Simulator Project.

The simulator is a simple spring boot application which acts as the TAPI controller for the MDONS use case

The API'S are as follows:

TAPI
1.Service interface points GET: /cxf/tapi/v2/connectivities/service-interface-points/{connection-point}
2.Create Service POST: /cxf/tapi/v2/connectivities/create-service/{service-name}
3.Delete Service: DELETE  /cxf/tapi/v2/connectivities/delete-service/{service-name}
4.Get Service: GET /cxf/tapi/v2/connectivities/get-service/{service-name}
5.Get Topology : GET  /cxf/tapi/v2/connectivities/topology

##Sample create request

{
   "create-connectivity-service-input-list":[
      {
         "end-point":[
            {
          "local-id": "/OTN/FW950074SDN-12/ODU0:1-2-2",

               "layer-protocol-name":"ODU",
               "connection-end-point":[
            {
              "topology-uuid": "754a91dc-dcd1-3530-8e95-a4880c298a1f",
              "node-edge-point-uuid": "5d90ffed-8240-337b-82cf-3a550c2e12da",
              "node-uuid": "492c08a7-6664-382b-8153-e8eed147b5b3",
              "connection-end-point-uuid": "a8855567-8699-3703-8d54-b4ec2da694b9"
            }
               ],
               "service-interface-point":{
                  "service-interface-point-uuid":"a8855567-8699-3703-8d54-b4ec2da694b9"
               },
               "protection-role":"WORK"
            },
            {            "local-id": "/OTN/FW950074SDN-15/ODU2:2-3-14-1",
               "layer-protocol-name":"ODU",
               "connection-end-point":[
            {
              "topology-uuid": "754a91dc-dcd1-3530-8e95-a4880c298a1f",
              "node-edge-point-uuid": "c4b07e05-871a-3811-881d-13983e3e28e0",
              "node-uuid": "9373a6b7-8d29-35bb-a5c9-dd823da138ae",
              "connection-end-point-uuid": "139c16e7-c13b-3f86-853e-1655afc2f167"
            }
               ],
               "service-interface-point":{
                  "service-interface-point-uuid":"139c16e7-c13b-3f86-853e-1655afc2f167"
               },
               "protection-role":"WORK"
            }
         ],
         "layer-protocol-name":"ODU",
         "connectivity-constraint":{
            "service-type":"POINT_TO_POINT_CONNECTIVITY"
         },
         "routing-constraint":{
            "route-objective-function":"MIN_WORK_ROUTE_COST"
         },
         "topology-constraint":[

         ],
         "name":[
            {
               "value-name":"service-name",
               "value":"demo-nni"
            }
         ]
      }
   ]
}

##To bring up locally along with the other instances go to the main directory dc-simluator
Execute docker-compose up


##To compile and build 
mvn clean install


##To build docker image 
mvn package -P docker





