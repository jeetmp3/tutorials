import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {SecurityServiceService} from "../security-service.service";

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent implements OnInit {

  tokenUrl = 'http://localhost:8080/login/oauth2/code/github';

  constructor(private route: ActivatedRoute,
              private http: HttpClient,
              private router: Router,
              private securityService: SecurityServiceService) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(p => {
      const state = p.state;
      const code = p.code;

      this.http.get(this.tokenUrl + '?code=' + code + '&state=' + state)
        .subscribe(d => {
          console.log(d);
          this.securityService.updateToken(d['access-token']);
          this.router.navigate(['/home']).then();
        })
    })
  }

}
