import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserPack, UserPack } from 'app/shared/model/user-pack.model';
import { UserPackService } from './user-pack.service';
import { UserPackComponent } from './user-pack.component';
import { UserPackDetailComponent } from './user-pack-detail.component';
import { UserPackUpdateComponent } from './user-pack-update.component';

@Injectable({ providedIn: 'root' })
export class UserPackResolve implements Resolve<IUserPack> {
  constructor(private service: UserPackService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserPack> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userPack: HttpResponse<UserPack>) => {
          if (userPack.body) {
            return of(userPack.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserPack());
  }
}

export const userPackRoute: Routes = [
  {
    path: '',
    component: UserPackComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.userPack.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserPackDetailComponent,
    resolve: {
      userPack: UserPackResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.userPack.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserPackUpdateComponent,
    resolve: {
      userPack: UserPackResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.userPack.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserPackUpdateComponent,
    resolve: {
      userPack: UserPackResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.userPack.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
