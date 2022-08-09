import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class BusService {
  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  addBus(data:any){
    return this.httpClient.post(this.url+
      "/bus/addBus",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  updateBus(data:any){
    return this.httpClient.post(this.url+
      "/bus/updateBus",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  getAllBus(){
    return this.httpClient.get(this.url+"/bus/getAllBus");
  }

  getAllBusByDate(data:any){
    return this.httpClient.post(this.url+
      "/bus/getAllBusByDate",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

}
