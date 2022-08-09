import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppUserService {
  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  signUp(data:any){
    return this.httpClient.post(this.url+
      "/appUser/signUp",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  logIn(data:any){
    return this.httpClient.post(this.url+
      "/appUser/logIn",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  changePassword(data:any){
    return this.httpClient.post(this.url+
      "/appUser/changePassword",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  forgotPassword(data:any){
    return this.httpClient.post(this.url+
      "/user/forgotPassword/",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }
}
