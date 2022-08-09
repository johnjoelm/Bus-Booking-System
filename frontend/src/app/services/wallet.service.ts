import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WalletService {
  url = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  addMoney(data: any) {
    return this.httpClient.post(this.url +
      "/wallet/addMoney", data, {
      headers: new HttpHeaders().set('Content-Type', "application/json")
    })
  }

  getAmountByUserId(auId:any){
    return this.httpClient.get(this.url+"/wallet/getAmountByUserId/"+auId);
  }
}
