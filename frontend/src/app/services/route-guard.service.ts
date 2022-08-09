import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { SnackbarService } from './snackbar.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(public router: Router, private snachbarService: SnackbarService) { }
  canActivate(route: ActivatedRouteSnapshot): boolean {

    if (localStorage.getItem('id') != undefined) {
      return true;
    }
    else {
      this.router.navigate(['/']);
      localStorage.clear();
      return false;
    }
  }
}
