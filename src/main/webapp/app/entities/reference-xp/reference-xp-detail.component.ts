import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferenceXP } from 'app/shared/model/reference-xp.model';

@Component({
  selector: 'jhi-reference-xp-detail',
  templateUrl: './reference-xp-detail.component.html'
})
export class ReferenceXPDetailComponent implements OnInit {
  referenceXP: IReferenceXP | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referenceXP }) => (this.referenceXP = referenceXP));
  }

  previousState(): void {
    window.history.back();
  }
}
