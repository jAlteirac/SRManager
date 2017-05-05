package alteirac.srmanager.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;
/**
 * Created by Guillaume on 05/05/2017.
 */

public class NewsSlideFragment extends Fragment {

    private Map<Integer, News> news = new HashMap<Integer, News>()
    {{
        put(0,new News("Les Rochelais qualifié pour la demi finale  !","Ce samedi 21 Avril les Rochelais ont joué les quarts de finale contre leur principaux rivaux : les Angoumoisins. L'anné dernière ils avaient encore une fois perdu contre ces derniers, mais cette année, à 3 minutes du coup de sifflet ce fut différent."));
        put(1,new News("Terrible defaite...","Ce week-end les Rochelais ont joué contre les fils de pute de capitaliste. Ces derniers ayant payé l'arbitre il fut impossible pour les Rochelais de ne serait-ce qu'un essai."));
        put(2,new News("Enfin qualifié ! ","Mercredi denier nous nous souvenons tous de la terrible defaite des Bordelais face aux Rennois. Demain ce sera au tour de nos Rochelais d'essayer de minimiser les dégats. Nous comptons sur vous les gars !"));
        put(3,new News("Bagarre dans les vestiaires des Rocheloise","La rencontre avec les Clermontoise fut rude. Non seulement elles sont connu pour avoir de superbe fesse... euh de superbe passe mais de plus elles sont très violente. En effet à la fin du match ces dernières sont allées mettre une bonne raclée à nos Rocheloise. Résultat des courses : 5 bléssées et 2 mortes"));
    }};

    public static NewsSlideFragment newInstance(int news_id) {

        NewsSlideFragment personalFeedFragment = new NewsSlideFragment();

        Bundle args = new Bundle();
        args.putInt("news_id", news_id);

        personalFeedFragment.setArguments(args);

        return personalFeedFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_news, container, false);

        final int news_id = getArguments().getInt("news_id");
        Log.d("DEBUG","news_id : "+news_id);

        TextView textTitle = (TextView) v.findViewById(R.id.NewsTitle);
        TextView textDesc = (TextView) v.findViewById(R.id.NewsDesc);

        textTitle.setText(news.get(news_id).getTitle());
        textDesc.setText(news.get(news_id).getDesc());

        return v;
    }

}
