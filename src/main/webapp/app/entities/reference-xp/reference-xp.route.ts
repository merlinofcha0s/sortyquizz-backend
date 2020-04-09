import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IReferenceXP, ReferenceXP } from 'app/shared/model/reference-xp.model';
import { ReferenceXPService } from './reference-xp.service';
import { ReferenceXPComponent } from './reference-xp.component';
import { ReferenceXPDetailComponent } from './reference-xp-detail.component';
import { ReferenceXPUpdateComponent } from './reference-xp-update.component';

@Injectable({ providedIn: 'root' })
export class ReferenceXPResolve implements Resolve<IReferenceXP> {
  constructor(private service: ReferenceXPService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReferenceXP> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((referenceXP: HttpResponse<ReferenceXP>) => {
          if (referenceXP.body) {
            return of(referenceXP.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ReferenceXP());
  }
}

export const referenceXPRoute: Routes = [
  {
    path: '',
    component: ReferenceXPComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.referenceXP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReferenceXPDetailComponent,
    resolve: {
      referenceXP: ReferenceXPResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.referenceXP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReferenceXPUpdateComponent,
    resolve: {
      referenceXP: ReferenceXPResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.referenceXP.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReferenceXPUpdateComponent,
    resolve: {
      referenceXP: ReferenceXPResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.referenceXP.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
