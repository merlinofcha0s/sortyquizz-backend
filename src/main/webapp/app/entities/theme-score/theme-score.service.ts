import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IThemeScore } from 'app/shared/model/theme-score.model';

type EntityResponseType = HttpResponse<IThemeScore>;
type EntityArrayResponseType = HttpResponse<IThemeScore[]>;

@Injectable({ providedIn: 'root' })
export class ThemeScoreService {
  public resourceUrl = SERVER_API_URL + 'api/theme-scores';

  constructor(protected http: HttpClient) {}

  create(themeScore: IThemeScore): Observable<EntityResponseType> {
    return this.http.post<IThemeScore>(this.resourceUrl, themeScore, { observe: 'response' });
  }

  update(themeScore: IThemeScore): Observable<EntityResponseType> {
    return this.http.put<IThemeScore>(this.resourceUrl, themeScore, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThemeScore>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThemeScore[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
