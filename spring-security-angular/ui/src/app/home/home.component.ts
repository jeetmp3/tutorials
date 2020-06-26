import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  name;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {

    this.getUserInfo().subscribe(data => this.name = data.name);
  }

  getUserInfo(): Observable<any> {
    return this.http.get(environment.baseUrl + '/v1/home');
  }
}
