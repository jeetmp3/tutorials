import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SecurityServiceService {

  private tokenKey = 'TOKEN';

  constructor() {
  }

  public getToken() {
    return localStorage.getItem(this.tokenKey);
  }

  public updateToken(token) {
    localStorage.setItem(this.tokenKey, token);
  }

  public isLoggedIn(): boolean {
    if (this.getToken()) {
      return true;
    }
    return false;
  }
}
