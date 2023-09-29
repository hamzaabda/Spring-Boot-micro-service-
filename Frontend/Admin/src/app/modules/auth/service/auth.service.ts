import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../../models/RegisterRequest';
import { AuthResponseData } from '../../models/AuthResponseData';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private AUTHURL: string;

  constructor(private http: HttpClient) {
      this.AUTHURL = environment.AUTHURL;
    }

    
    register(RegisterRequest:RegisterRequest) {
      const registerUrl = this.AUTHURL+'/register';
      return this.http.post<AuthResponseData>(registerUrl,RegisterRequest); 
    }


}
