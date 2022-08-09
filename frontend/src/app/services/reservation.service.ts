import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  getBookedSeats(data:any){
    return this.httpClient.post(this.url+
      "/reservation/getBookedSeats",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  reserveSeat(data:any){
    return this.httpClient.post(this.url+
      "/reservation/reserveSeat",data,{
        headers: new HttpHeaders().set('Content-Type',"application/json")
      })
  }

  getReservationByUserId(auId:any,status:any){
    return this.httpClient.get(this.url+"/reservation/getReservationByUserId/"+auId+"/"+status);
  }

  cancleBooking(reId:any){
    return this.httpClient.get(this.url+"/reservation/cancleBooking/"+reId);
  }
}
