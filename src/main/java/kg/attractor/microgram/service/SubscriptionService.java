package kg.attractor.microgram.service;

import kg.attractor.microgram.model.Subscription;
import kg.attractor.microgram.model.User;
import kg.attractor.microgram.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    @Transactional
    public void subscribe(Long authorId, String login) {
        User subscriber = userService.getByLogin(login);
        User author = userService.getById(authorId);
        if (subscriber.getId().equals(authorId)) {
            throw new IllegalArgumentException("Нельзя подписаться на себя");
        }
        if (isSubscribed(subscriber.getId(), authorId)) {
            return;
        }
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setAuthor(author);
        subscriptionRepository.saveAndFlush(subscription);
        log.info("Пользователь {} подписался на пользователя {}", login, authorId);
    }

    @Transactional
    public void unsubscribe(Long authorId, String login) {
        User subscriber = userService.getByLogin(login);
        userService.getById(authorId);
        subscriptionRepository.findBySubscriberIdAndAuthorId(subscriber.getId(), authorId)
                .ifPresent(subscriptionRepository::delete);
        log.info("Пользователь {} отписался от пользователя {}", login, authorId);
    }

    public boolean isSubscribed(Long subscriberId, Long authorId) {
        return subscriptionRepository.existsBySubscriberIdAndAuthorId(subscriberId, authorId);
    }

    public long countSubscriptions(Long userId) {
        return subscriptionRepository.countBySubscriberId(userId);
    }

    public long countSubscribers(Long userId) {
        return subscriptionRepository.countByAuthorId(userId);
    }
}
