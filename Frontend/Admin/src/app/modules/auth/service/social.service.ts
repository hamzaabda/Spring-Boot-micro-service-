import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SocialService {

   SOCIAL_URL : string;

  constructor(private http:HttpClient) { 
    this.SOCIAL_URL = environment.SOCIAL_URL;
  }


  loginWithGoogle(token) :Observable<any>
  {
    return this.http.post(`${this.SOCIAL_URL}google`, {token});
  }

  loginWithFacebook(token) :Observable<any>
  {
    return this.http.post(`${this.SOCIAL_URL}facebook`,{token}).pipe(
      map(
        response => {return response}
      )

    );
  }

}
