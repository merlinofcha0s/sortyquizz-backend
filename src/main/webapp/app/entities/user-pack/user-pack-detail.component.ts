import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserPack } from 'app/shared/model/user-pack.model';

@Component({
  selector: 'jhi-user-pack-detail',
  templateUrl: './user-pack-detail.component.html'
})
export class UserPackDetailComponent implements OnInit {
  userPack: IUserPack | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPack }) => (this.userPack = userPack));
  }

  previousState(): void {
    window.history.back();
  }
}
