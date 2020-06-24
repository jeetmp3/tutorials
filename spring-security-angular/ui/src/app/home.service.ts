import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private userProfileUrl = environment.serverUrl + '/v1/user/info';
  constructor(private http: HttpClient) { }

  getUserInfo(): Observable<any> {
    return this.http.get(this.userProfileUrl);
  }
}
