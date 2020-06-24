import {Component, OnInit} from '@angular/core';
import {HomeService} from "../home.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  username: string;

  constructor(private homeService: HomeService) {
  }

  ngOnInit(): void {
    this.homeService.getUserInfo().subscribe(data => this.username = data.name);
  }

}
