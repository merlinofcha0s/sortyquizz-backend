import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReferenceXP } from 'app/shared/model/reference-xp.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ReferenceXPService } from './reference-xp.service';
import { ReferenceXPDeleteDialogComponent } from './reference-xp-delete-dialog.component';

@Component({
  selector: 'jhi-reference-xp',
  templateUrl: './reference-xp.component.html'
})
export class ReferenceXPComponent implements OnInit, OnDestroy {
  referenceXPS: IReferenceXP[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected referenceXPService: ReferenceXPService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.referenceXPS = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.referenceXPService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IReferenceXP[]>) => this.paginateReferenceXPS(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.referenceXPS = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReferenceXPS();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReferenceXP): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReferenceXPS(): void {
    this.eventSubscriber = this.eventManager.subscribe('referenceXPListModification', () => this.reset());
  }

  delete(referenceXP: IReferenceXP): void {
    const modalRef = this.modalService.open(ReferenceXPDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.referenceXP = referenceXP;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateReferenceXPS(data: IReferenceXP[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.referenceXPS.push(data[i]);
      }
    }
  }
}
