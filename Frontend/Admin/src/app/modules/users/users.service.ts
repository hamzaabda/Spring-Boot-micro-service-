import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  ADMIN_URL = 'http://localhost:9000/api/user/admin/'
  constructor(private http:HttpClient) { }


  getalluseradmin() :Observable<any>
  {
    const requestURL = this.ADMIN_URL+'getusers'
    return this.http.get<any>(requestURL);

  }

  deleteuser(idUser:number)
  {
    const requestURL = this.ADMIN_URL+`deleteuserbyid/${idUser}`

    return this.http.delete(requestURL);

  }


  fetchUserById(idUser:number)
  {
    const requestURL = this.ADMIN_URL+`getuserbyid/${idUser}`
    return this.http.get(requestURL);

  }

  editUser(user:any,idUser:number)
  {
    const requestURL = this.ADMIN_URL+`edituser/${idUser}`;
    return this.http.put(requestURL,user);

  }

}
