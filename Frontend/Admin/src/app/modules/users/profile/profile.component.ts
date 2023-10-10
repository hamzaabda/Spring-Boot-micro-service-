import { Component, OnInit } from '@angular/core';

import jwtDecode from 'jwt-decode';
import { AuthService } from 'src/app/modules/auth/service/auth.service';

import { ChartType } from './profile.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})

/**
 * Contacts-profile component
 */
export class ProfileComponent implements OnInit {
  // bread crumb items
  breadCrumbItems: Array<{}>;

  revenueBarChart: ChartType;
  statData;
  connecteduser;
  constructor(private AuthService:AuthService) { }

  ngOnInit() {
    this.breadCrumbItems = [{ label: 'User' }, { label: 'Profile', active: true }];

    if(localStorage.getItem('access_token') !== null || localStorage.getItem('refresh_token') !== null)
    {
    const access_token  = localStorage.getItem('access_token');
    const refresh_token = localStorage.getItem('refresh_token');
    interface DecodedToken {
      sub: string;
      Role: string; 
      exp : string;
      iat: string;
    }
      const decoded: DecodedToken = jwtDecode(access_token);
      const sub = decoded.sub;
      const roles = decoded.Role;
      const exp = decoded.exp;
      const iat = decoded.iat;
      console.log(sub + roles + exp + iat);
      this.AuthService.getuserbyemail(sub).subscribe(
        (data) => {
          console.log(data)
          this.connecteduser = data
          console.log(data?.id)
          console.log(data?.username)
          console.log(data?.email)
          console.log(data?.nom)
          console.log(data?.prenom)
          console.log(data?.isEnabled)
        }

      );
    }
    else{
      this.connecteduser = null;
    }
  }

  /**
   * Fetches the data
   */

}
